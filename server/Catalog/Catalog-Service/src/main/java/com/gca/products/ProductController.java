package com.gca.products;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	
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
	
	@Autowired
	private ProductService productService;
	
	private Logger LOG = LoggerFactory.getLogger(ProductController.class);
	

	@RequestMapping("/products")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Product> getAll(HttpServletRequest request) {
		LOG.info("EndpointMapping -> GET: " + request.getRequestURL().toString());
		return productService.getAll();
	}

	@RequestMapping("/product/")
	@CrossOrigin(origins = "http://localhost:4200")
	public @ResponseBody Product getProduct(HttpServletRequest request, @RequestParam("id") int id) {
		LOG.info("EndpointMapping -> GET: " + request.getRequestURL().toString());
		//LOG.info("http.GET on '/product/'");
		return productService.getProduct(id);
	}

	@RequestMapping("/product/{name}")									//Mit Umgebungsvariable
	@CrossOrigin(origins = "http://localhost:4200")
	public Product getProduct(HttpServletRequest request, @PathVariable("name") String name) {
		LOG.info("EndpointMapping -> GET: " + request.getRequestURL().toString());
		if (name.matches("[0-9]+")) {
			return productService.getProduct(Integer.valueOf(name));
		} else {
			return productService.getProduct(name);
		}

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
