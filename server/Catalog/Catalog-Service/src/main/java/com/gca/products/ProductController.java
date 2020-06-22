package com.gca.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping("/products")
	public List<Product> getAll() {
		return productService.getAll();
	}

	@RequestMapping("/product/")
	public @ResponseBody Product getProduct(@RequestParam("id") int id) {
		return productService.getProduct(id);
	}

	@RequestMapping("/product/{name}")
	public Product getProduct(@PathVariable("name") String name) {

		if (name.matches("[0-9]+")) {
			return productService.getProduct(Integer.valueOf(name));
		} else {
			return productService.getProduct(name);
		}

	}

}
