package com.gca.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {
	
	@Autowired
	private CheckoutService checkoutService;
	
	@RequestMapping("/checkout")
	public double getShipping() {
		return 0;
	}

}
