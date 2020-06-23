package com.gca.order;


public class Product {
	
	int id;
	double price;
	String name;
	String image;
	
	public Product(int id, double price, String name) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
	}
	
	public Product(int id, double price, String name, String image) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
		this.image = image;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	

}
