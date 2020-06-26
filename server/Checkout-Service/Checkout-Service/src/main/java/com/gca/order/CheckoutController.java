package com.gca.order;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
@RestController
public class CheckoutController {
	
	@Autowired
	private CheckoutService checkoutService;
	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
		
	
	private Logger LOG = LoggerFactory.getLogger(CheckoutController.class);
	private static final String MAIN_SERVICE = "mainService";
	
	private String ip = "localhost";
	
//	@RequestMapping(
//		    value = "/checkout", 
//		    method = RequestMethod.POST)
//		public void process(@RequestBody Map<String, Object> payload) 
//		    throws Exception {
//
//		  System.out.println(payload);
//
//		}
	@Bean
	private void IpLoader() {
		try {
			ip = InetAddress.getLocalHost().getHostAddress().toString();
			LOG.info("Microservice startet on ip: " + ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/checkout")
	@CrossOrigin(origins = "http://localhost:4200")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "test")
	public int checkout(@RequestBody Order order) {
		LOG.info("http.POST on '/checkout'");
		restTemplate.getForObject(("http://" + ip + ":8084/ping"), String.class);
		
		
		//Response response = new OkHttpClient().newCall(new Request.Builder().url("http://" + ip + ":8084/cart").build()).execute();
		return checkoutService.checkout(order);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/orders/{ordNumber}") //Hier nutzen wir eine dynamische URL mit Umgebungsvariable
	@CrossOrigin(origins = "http://localhost:4200")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "fallback")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Order getOrder(@PathVariable("ordNumber") int ordNumber) {
		LOG.info("http.GET on '/orders/{" + ordNumber + "}'");
		try {
			restTemplate.getForObject(("http://" + ip + ":8084/ping"), String.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkoutService.getOrder(ordNumber);
	}
	
	private Order fallback(Exception e) {
		LOG.error("#################### Pech!!!!!!!!!!!!!!!");
		return null;
	}
	
//	private ResponseEntity<String> test(Exception e) {
//		return new ResponseEntity<String>("hallo", null);
//	}
	
	

}
