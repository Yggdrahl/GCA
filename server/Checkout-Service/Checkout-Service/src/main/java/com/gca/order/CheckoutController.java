package com.gca.order;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {
	
	@Autowired
	private CheckoutService checkoutService;
	
	private Logger LOG = LoggerFactory.getLogger(CheckoutController.class);
	
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
	public int checkout(@RequestBody Order order) {
		LOG.info("http.POST on '/checkout'");
		return checkoutService.checkout(order);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/orders/{ordNumber}") //Hier nutzen wir eine dynamische URL mit Umgebungsvariable
	@CrossOrigin(origins = "http://localhost:4200")
	@ResponseBody
	public Order getOrder(@PathVariable("ordNumber") int ordNumber) {
		LOG.info("http.GET on '/orders/{" + ordNumber + "}'");
		return checkoutService.getOrder(ordNumber);
	}
	
	

}
