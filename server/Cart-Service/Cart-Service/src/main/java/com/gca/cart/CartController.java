package com.gca.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
		
	@RequestMapping("/cart")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Product> getCart() {
		return cartService.getAll();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cart")
	@CrossOrigin(origins = "http://localhost:4200")
	public void insertIntoCart(@RequestBody Product product) {
		cartService.add(product);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/cart")
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteFromCart(@RequestBody Product id) {
		cartService.remove(id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/emptyCart")
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteCart() {
		cartService.removeAll();
	}

}
