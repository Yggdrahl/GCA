package com.gca.cart;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jdk.internal.org.jline.utils.Log;

@Service
public class CartService {
	
	private List<Product> cartRepo = new ArrayList<>();
	private Logger LOG = LoggerFactory.getLogger(CartService.class);

	
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
				LOG.error("The Product ID:" + product.getId() + " is already in the cart");
				return false;
			}
		}
		this.cartRepo.add(product);
		LOG.info("The Product ID:" + product.getId() + " as added to the cart (" + cartRepo.size() + " Products)");
		return true;
		
	}
	
	public List<Product> getAll() {
		return this.cartRepo;
	}
	
	public boolean remove(int id) {
		
		
		for(int i = 0; i < this.cartRepo.size(); i++) {
			if(this.cartRepo.get(i).id == id) {
				LOG.info("The Product ID:" + id + " was removed from the cart");
				this.cartRepo.remove(i);
				return true;
			}
		}
		LOG.info("There is no such Product ID:" + id);
		return false;
	}
	
	public boolean removeAll() {
		this.cartRepo.clear();
		LOG.info("The cart is now empty (" + cartRepo.size() + " Products)");
		return true;
	}
}
