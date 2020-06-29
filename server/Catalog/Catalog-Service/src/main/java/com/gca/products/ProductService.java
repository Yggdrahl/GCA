package com.gca.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	private Logger LOG = LoggerFactory.getLogger(ProductService.class);

	private List<Product> repo = new ArrayList<Product>(
			Arrays.asList(new Product(1, 149.98, "Vintage-Objektiv", "assets/images/products/camera-lens.jpg"),
					new Product(2, 15.98, "Schreibmaschine", "assets/images/products/typewriter.jpg"),
					new Product(3, 5489.98, "Terrarium", "assets/images/products/terrarium.jpg"),
					new Product(4, 854.98, "Barista Kit", "assets/images/products/barista-kit.jpg"),
					new Product(5, 54.98, "Film-Kamera", "assets/images/products/film-camera.jpg"),
					new Product(6, 21.98, "Plattenspieler", "assets/images/products/record-player.jpg"),
					new Product(7, 21.98, "Camp-Tasse", "assets/images/products/camp-mug.jpg"),
					new Product(8, 21.98, "Hollandrad", "assets/images/products/city-bike.jpg"),
					new Product(9, 21.98, "Zimmerpflanze", "assets/images/products/air-plant.jpg"),
					new Product(10, 400.00, "Fork to go", "assets/images/products/fork-to-go.jpg")
					));

	public List<Product> getAll() {
		return this.repo;
	}

	public Product getProduct(int id) {

		for (int i = 0; i < this.repo.size(); i++) {
			if (this.repo.get(i).id == id) {
				LOG.info("Product ID:" + id + " found in the catalog-service");
				return this.repo.get(i);
			}
		}
		LOG.info("404 | Product ID:" + id + " not found in the catalog-service");
		return null;
	}

	public Product getProduct(String name) {

		for (int i = 0; i < this.repo.size(); i++) {
			if (this.repo.get(i).name.equals(name)) {
				LOG.info("First Product with Name:" + name + " found in the catalog-service");
				return this.repo.get(i);
			}
		}
		LOG.info("404 | Product Name:" + name + " not found in the catalog-service");
		return null;
	}

}
