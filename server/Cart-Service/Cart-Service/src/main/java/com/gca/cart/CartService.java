package com.gca.cart;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {
	
	private List<Product> cartRepo = new ArrayList<>();

	
	public String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "localhost";
		}
	}

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
	
	public boolean remove(int id) {
		
		
		for(int i = 0; i < this.cartRepo.size(); i++) {
			if(this.cartRepo.get(i).id == id) {
				this.cartRepo.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeAll() {
		this.cartRepo.clear();
		return true;
	}
}
