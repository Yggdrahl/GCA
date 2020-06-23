package com.gca.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {
	
	private List<Product> cartRepo = new ArrayList<>();
	

	public boolean add(Product product) {
		for(int i = 0; i < this.cartRepo.size(); i++) {
			if(this.cartRepo.get(i).id == product.id) {
				return false;
			}
		}
		this.cartRepo.add(product);
		return true;
		
	}
	
	public List<Product> getAll() {
		return this.cartRepo;
	}
}
