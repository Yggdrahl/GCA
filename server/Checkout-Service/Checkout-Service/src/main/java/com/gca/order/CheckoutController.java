package com.gca.order;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
public class CheckoutController {
	
	private String authPW = "testpw";
	
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
	@CircuitBreaker(name = MAIN_SERVICE)
	public int checkout(HttpServletRequest request, @RequestBody Order order) {
		//LOG.info("http.POST on '/checkout'");
		
		//restTemplate.getForObject(("http://" + ip + ":8084/ping"), String.class);
		//Response response = new OkHttpClient().newCall(new Request.Builder().url("http://" + ip + ":8084/cart").build()).execute();
		
		//return checkoutService.checkout(order);
		LOG.info("EnpointMapping -> GET: " + request.getRequestURL().toString());
		if(authorization(request)) {
			LOG.info("Authentification correct");
						
			return checkoutService.checkout(order);
		}
		LOG.error("Authentification incorrect");
		return -1;
		
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/orders/{ordNumber}") //Hier nutzen wir eine dynamische URL mit Umgebungsvariable
	@CrossOrigin(origins = "http://localhost:4200")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "test")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Order getOrder(HttpServletRequest request, @PathVariable("ordNumber") int ordNumber) {
		//LOG.info("http.GET on '/orders/{" + ordNumber + "}'");
		//return checkoutService.getOrder(ordNumber);
		//return restTemplate.getForObject(("http://" + ip + ":8084/ping"), String.class);
		LOG.info("EnpointMapping -> GET: " + request.getRequestURL().toString());
		if(authorization(request)) {
			LOG.info("Authentification correct");
						
			return checkoutService.getOrder(ordNumber);
		}
		LOG.error("Authentification incorrect");
		return null;
	}
	
	
	private Order test(Exception e) {
		System.out.println("Ich bin ein Fallback!");
		return null;
	}
	
	
	public boolean authorization(HttpServletRequest request) {
		
		if(request.getHeader("Authorization") != null) {
			
			String header = request.getHeader("Authorization");
			
			if(header.equals(authPW) || header.equals("ng")) {
				return true;
			} else {
				return false;
			}
				
		}
		return false;
	}
	
	

}
