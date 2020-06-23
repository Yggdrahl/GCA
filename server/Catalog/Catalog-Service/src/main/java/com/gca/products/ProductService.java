package com.gca.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

	private List<Product> repo = new ArrayList<Product>(
			Arrays.asList(new Product(1, 149.98, "Camera"), new Product(2, 15.98, "Typewriter"),
					new Product(3, 5489.98, "Greenhouse"), new Product(4, 854.98, "Barista Kit"),
					new Product(5, 54.98, "Vintage Camera"), new Product(6, 21.98, "Turntable"), new Product(7, 21.98, "camp-mug", "assets/products/camp-mug.jpg")));

	public List<Product> getAll() {
		return this.repo;
	}

	public Product getProduct(int id) {

		for (int i = 0; i < this.repo.size(); i++) {
			if (this.repo.get(i).id == id) {
				return this.repo.get(i);
			}
		}
		return null;
	}

	public Product getProduct(String name) {

		for (int i = 0; i < this.repo.size(); i++) {
			if (this.repo.get(i).name.equals(name)) {
				return this.repo.get(i);
			}
		}
		return null;
	}

}
