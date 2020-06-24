package com.gca.order;

import java.util.List;

public class Order {
	
	//private String user;
	private List<Product> cart;
	private double sum;
	private double shipping;
	private String tracking;
	private int ordernummer;
	
	private String mail;
	private String street;
	private String city;
	private String state;
	private String country;
	private String month;
	private String cvv; //
	private int year;
	private int zip;
	private int creditcard;
	
	public Order(String mail, String street, String city, String state, String country, String month, String cvv, int year, int zip, int creditcard) {
		super();
		//this.cart = cart;
		//this.shipping = shipping;
		//this.tracking = tracking;
		this.mail = mail;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.month = month;
		this.cvv = cvv;
		this.year = year;
		this.zip = zip;
		this.creditcard = creditcard;
		
	}
	
	private double calcSum() {
		double tmp = 0;
		if(this.cart != null && this.cart.size() > 0) {
			for(int i = 0; i < this.cart.size(); i++) {
				tmp = tmp + this.cart.get(i).getPrice(); 
			}
		}
		return tmp;
	}

	//Getters and Setters
	
	public List<Product> getCart() {
		return cart;
	}

	public void setCart(List<Product> cart) {
		this.cart = cart;
		this.sum = calcSum();
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public String getTracking() {
		return tracking;
	}

	public void setTracking(String tracking) {
		this.tracking = tracking;
	}

	public int getOrdernummer() {
		return ordernummer;
	}
	
	public void setOrdernummer(int ordernummer) {
		this.ordernummer = ordernummer;
	}
	
	public double getSum() {
		return sum;
	}
	
	public void setSum(double sum) {
		this.sum = sum;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public int getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(int creditcard) {
		this.creditcard = creditcard;
	}
	
	
	
	
	

}
