package com.gca.cart;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
	
	private Logger LOG = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartService cartService;
		
	@RequestMapping("/cart")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Product> getCart() {
		LOG.info("http.GET on '/cart'");
		return cartService.getAll();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cart")
	@CrossOrigin(origins = "http://localhost:4200")
	public void insertIntoCart(@RequestBody Product product) {
		LOG.info("http.POST on '/cart' | Body: \"id\":" + product.getId());
		cartService.add(product);
	}
		
	@RequestMapping(method = RequestMethod.DELETE, value = "/cart/{id}") //Hier nutzen wir eine dynamische URL mit Umgebungsvariable
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteFromCart(@PathVariable("id") int id) {
		LOG.info("http.DELETE on '/cart' (Product.id=" + id + ")");
		cartService.remove(id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/emptyCart")
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteCart() {
		LOG.info("http.DELETE on '/emptyCart' (empties the whole cart)");
		cartService.removeAll();
	}

}
