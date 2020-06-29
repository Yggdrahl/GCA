package com.gca.order;

import java.time.Duration;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

@RestController
@EnableRetry
public class CheckoutController {

	private String authPW = "testpw";

	@Autowired
	private CheckoutService checkoutService;

	private Logger LOG = LoggerFactory.getLogger(CheckoutController.class);

	// ---------------------------------------------------------------
	// API Endpoints
	// ---------------------------------------------------------------

	@RequestMapping(method = RequestMethod.POST, value = "/checkout")
	@CrossOrigin(origins = "http://localhost:4200")
	//@RateLimiter(name = "CheckoutService", fallbackMethod = "getFallbackCheckout")
	@HystrixCommand(
					// Circuitbreaker
					fallbackMethod = "getFallbackCheckout",
					// Bulkhead
					threadPoolKey = "checkoutPool", threadPoolProperties = {
							@HystrixProperty(name = "coreSize", value = "20"),	// Wie viele Threads sollen höchstens auf eine Antwort von checkout warten
							@HystrixProperty(name = "maxQueueSize", value = "10") // Wie viele sollen höchstens in der Warteschlange warten, bevor die zugang auf den Thread bekommen
	})
	// Retry
	@Retryable (value = {RuntimeException.class}, maxAttempts = 4, backoff = @Backoff (2000))
	public int checkout(HttpServletRequest request, @RequestBody Order order) {
		LOG.info("EnpointMapping -> GET: " + request.getRequestURL().toString());
		if (authorization(request)) {
			LOG.info("Authentification correct");

			return checkoutService.checkout(order);
		}
		LOG.error("Authentification incorrect");
		return -1;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/orders/{ordNumber}") // Hier nutzen wir eine dynamische URL mit Umgebungsvariable
	@CrossOrigin(origins = "http://localhost:4200")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	//@RateLimiter(name = "CheckoutService", fallbackMethod = "getFallbackOrder")
	@HystrixCommand(
					// Circuitbreaker
					fallbackMethod = "getFallbackOrder",
					// Bulkhead
					threadPoolKey = "checkoutPool", threadPoolProperties = {
							@HystrixProperty(name = "coreSize", value = "20"),	// Wie viele Threads sollen höchstens auf eine Antwort von checkout warten
							@HystrixProperty(name = "maxQueueSize", value = "10") // Wie viele sollen höchstens in der Warteschlange warten, bevor die zugang auf den Thread bekommen
	})
	@Retryable (value = {RuntimeException.class}, maxAttempts = 4, backoff = @Backoff (2000))
	public Order getOrder(HttpServletRequest request, @PathVariable("ordNumber") int ordNumber) {
		LOG.info("EnpointMapping -> GET: " + request.getRequestURL().toString());
		if (authorization(request)) {
			LOG.info("Authentification correct");
			return checkoutService.getOrder(ordNumber);
		}
		LOG.error("Authentification incorrect");
		return null;
	}

	// ---------------------------------------------------------------
	// Resilience Fallbacks and Middelware
	// ---------------------------------------------------------------

	// Timeout
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		
		return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(3)).setReadTimeout(Duration.ofSeconds(3))
				.build();
	}

	/*
	 * Funktioniert noch nicht public int getFallbackStocks(Exception e) { return
	 * checkoutService.checkout(order); }
	 */
	
	// Bulkhead and Circuit Breaker
	public int getFallbackCheckout(HttpServletRequest request, @RequestBody Order order) {
		LOG.error("Circuitbreaker activatet");
		return -1;
	}

	public Order getFallbackOrder(HttpServletRequest request, @PathVariable("ordNumber") int ordNumber) {
		LOG.error("Circuittbreaker activatet");
		return null;
	}
	
	// Retry
	@Recover
	public String recover(Throwable t) {
		LOG.info("Checkout.Controller.recover");
		return "Error Class ::" + t.getClass().getName();
		}


	// ---------------------------------------------------------------
	// Security-Middelware
	// ---------------------------------------------------------------

	public boolean authorization(HttpServletRequest request) {

		if (request.getHeader("Authorization") != null) {

			String header = request.getHeader("Authorization");

			if (header.equals(authPW) || header.equals("ng")) {
				return true;
			} else {
				return false;
			}

		}
		return false;
	}

}
