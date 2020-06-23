package com.gca.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
		
	@RequestMapping("/cart")
	public List<Product> getCart() {
		return cartService.getAll();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cart")
	public void insertIntoCart(@RequestBody Product product) {
		cartService.add(product);
	}

}
