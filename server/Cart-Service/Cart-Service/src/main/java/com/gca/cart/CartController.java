package com.gca.cart;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
	
	@Value("${authentication.password}")
	private String authPW;
	
	@Bean
	public void checkAuthenticator() {
		if(authPW == null || authPW == "" || authPW.contentEquals("")) {
			authPW = "testPW";
			LOG.info("Authenticator was invalid and had to be reset");
		} else {
			LOG.info("Authenticator was set trough 'application.properties'");
		}
	}
	
	private Logger LOG = LoggerFactory.getLogger(CartController.class);
		
	@Autowired
	private CartService cartService;
	
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
		
	@RequestMapping("/cart")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Product> getCart(HttpServletRequest request) {
		
		LOG.info("EndpointMapping -> GET: " + request.getRequestURL().toString());
		if (authorization(request)) {
			LOG.info("Authentification correct");
			return cartService.getAll();
		}
		LOG.error("Authentification incorrect (Requests shouldn't be anonymus)");
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cart")
	@CrossOrigin(origins = "http://localhost:4200")
	public void insertIntoCart(HttpServletRequest request, @RequestBody Product product) {
		
		LOG.info("EndpointMapping -> POST: " + request.getRequestURL().toString());
		if (authorization(request)) {
			LOG.info("Authentification correct");
			LOG.info("http.POST on '/cart' | Body: \"id\":" + product.getId());
			cartService.add(product);
		} else {
			LOG.error("Authentification incorrect (Requests shouldn't be anonymus)");
		}	
	}
		
	@RequestMapping(method = RequestMethod.DELETE, value = "/cart/{id}") //Hier nutzen wir eine dynamische URL mit Umgebungsvariable
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteFromCart(HttpServletRequest request, @PathVariable("id") int id) {
		
		LOG.info("EndpointMapping -> DELETE: " + request.getRequestURL().toString());
		if (authorization(request)) {
			LOG.info("Authentification correct");
			LOG.info("http.DELETE on '/cart' (Product.id=" + id + ")");
		cartService.remove(id);
			
		} else {
			LOG.error("Authentification incorrect (Requests shouldn't be anonymus)");		
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/emptyCart")
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteCart(HttpServletRequest request) {
		
		LOG.info("EndpointMapping -> DELETE: " + request.getRequestURL().toString());
		if (authorization(request)) {
			LOG.info("Authentification correct");
			LOG.info("http.DELETE on '/emptyCart' (empties the whole cart)");
		cartService.removeAll();
			
		} else {
			LOG.error("Authentification incorrect (Requests shouldn't be anonymus)");		
		}
		
		
		
	}
	
//	@GetMapping("/validate") //Kleiner Test-Endpunkt
//	@CrossOrigin(origins = "http://localhost:4200")
//	//public String validate(@RequestHeader("Authorization") String Authorization, HttpServletRequest request )  {
//	public List<Product> validate(HttpServletRequest request)  {
//		
//		LOG.info("EnpointMapping -> GET: " + request.getRequestURL().toString());
//		if(authorization(request)) {
//			LOG.info("Authentification correct");
//			
//			List<Product> liste = new ArrayList<Product>();
//			liste.add(new Product(99, 99.99, "teures Ding", ""));
//			
//			return liste;
//		}
//		LOG.error("Authentification incorrect");
//		return null;
//			
//	}
	
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
