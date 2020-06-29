package com.gca.products;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	
	private String authPW = "testpw";
	
	@Autowired
	private ProductService productService;
	
	private Logger LOG = LoggerFactory.getLogger(ProductController.class);
	

	@RequestMapping("/products")
	@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8083" })
	public List<Product> getAll() {
		LOG.info("http.GET on '/products'");
		return productService.getAll();
	}

	@RequestMapping("/product/")
	@CrossOrigin(origins = "http://localhost:4200")
	public @ResponseBody Product getProduct(@RequestParam("id") int id) {
		LOG.info("http.GET on '/product/'");
		return productService.getProduct(id);
	}

	@RequestMapping("/product/{name}")									//Mit Umgebungsvariable
	@CrossOrigin(origins = "http://localhost:4200")
	public Product getProduct(@PathVariable("name") String name) {

		if (name.matches("[0-9]+")) {
			LOG.info("http.GET on '/product/{" + name + "}' | Could be Number or String" );
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
