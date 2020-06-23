package com.gca.order;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {
	
	@Autowired
	private CheckoutService checkoutService;
	
//	@RequestMapping(
//		    value = "/checkout", 
//		    method = RequestMethod.POST)
//		public void process(@RequestBody Map<String, Object> payload) 
//		    throws Exception {
//
//		  System.out.println(payload);
//
//		}
	
	@RequestMapping("/checkout")
	@CrossOrigin(origins = "http://localhost:4200")
	public void checkout() {
		checkoutService.getCart();
	}
	
	

}
