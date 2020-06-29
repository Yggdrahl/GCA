package com.gca.shipping;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {
	
	private String authPW = "testpw";
	
	@Autowired
	private ShippingService shippingService;
	
	private Logger LOG = LoggerFactory.getLogger(ShippingController.class);
	
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
	
	@RequestMapping("/getShippingCosts")
	@CrossOrigin(origins = "http://localhost:4200")
	public @ResponseBody double getShippingCosts(HttpServletRequest request, @RequestParam("costs") double costs) {
		LOG.info("EndpointMapping -> GET: " + request.getRequestURL().toString());
		if (authorization(request)) {
			LOG.info("Authentification correct");
			return shippingService.getShippingCosts(costs);
		}
		LOG.error("Authentification incorrect (Requests shouldn't be anonymus)");
		return 10;
	
	}
	
	@RequestMapping("/getTracking")
	@CrossOrigin(origins = "http://localhost:4200")
	public String getShippingCosts(HttpServletRequest request) {
		LOG.info("EndpointMapping -> GET: " + request.getRequestURL().toString());
		if (authorization(request)) {
			LOG.info("Authentification correct");
			return shippingService.generateTracking();
		}
		LOG.error("Authentification incorrect (Requests shouldn't be anonymus)");
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
