package com.gca.order;

import java.util.List;

public class Order {
	
	//private String user;
	private List<Product> cart;
	private double sum;
	private double shipping;
	private String tracking;
	private int ordernummer;
	private double totalSum;
	
	private String mail;
	private String street;
	private String city;
	private String state;
	private String country;
	private String month;
	private String cvv; //
	private int year;
	private int zip;
	private long creditcard;
	
	public Order(String mail, String street, String city, String state, String country, String month, String cvv, int year, int zip, long creditcard) {
		super();
		//this.cart = cart;
		//this.shipping = shipping;
		//this.tracking = tracking;
		this.mail = mail;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country; 
		switch(month) {
		case "1":
			this.month = "January";
			break;
		case "2":
			this.month = "February";
			break;
		case "3":
			this.month = "March";
			break;
		case "4":
			this.month = "April";
			break;
		case "5":
			this.month = "May";
			break;
		case "6":
			this.month = "June";
			break;
		case "7":
			this.month = "July";
			break;
		case "8":
			this.month = "August";
			break;
		case "9":
			this.month = "September";
			break;
		case "10":
			this.month = "October";
			break;
		case "11":
			this.month = "November";
			break;
		case "12":
			this.month = "December";
			break;
		}
		this.cvv = cvv;
		switch (year) {
		case 1:
			this.year = 2020;
			break;
		case 2:
			this.year = 2021;
			break;
		case 3:
			this.year = 2022;
			break;
		case 4:
			this.year = 2023;
			break;
		case 5:
			this.year = 2024;
			break;
		case 6: 
			this.year = 2025;
			break;
		}
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
		return Math.round(tmp * 100.0) / 100.0;
	}

	//Getters and Setters
	
	public List<Product> getCart() {
		return cart;
	}

	public void setCart(List<Product> cart) {
		this.cart = cart;
		this.sum = calcSum();
		this.totalSum = this.shipping + this.sum;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
		if(this.sum > 0) {
			this.totalSum = this.shipping + this.sum;
		}
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
	
	public double getTotalSum() {
		return this.totalSum;
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

	public long getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(long creditcard) {
		this.creditcard = creditcard;
	}
	
	
	public String toString() {
		
		String str = "";
		
		str = str + "Order-Nr.:\u0009" + this.ordernummer + "\n";
		str = str + "-------------------------------------------------\nEinkauf:\n";
		for(int i = 0; i < this.cart.size(); i++) {
			str = str + "\u0009\u0009" + this.cart.get(i).name + "\u0009" + this.cart.get(i).price + " €\n";
		}
		str = str + "=======\n";
		str = str + "\u0009" + this.sum + " €\n";
		str = str + "+\u0009" + this.shipping + " €\n";
		str = str + "(=)\u0009" + this.totalSum + " €\n";
		str = str + "-------------------------------------------------\n";
		str = str + "Tracking-Nr.:\u0009" + this.tracking + "\n";
		str = str + "Mail:\u0009\u0009" + this.mail + "\n";
		str = str + "Lieferadresse:\u0009" + this.zip + " " +  this.street + " " + this.state + ", " + this.country + "\n";
		str = str + "Zahlungsinformationen:\u0009\n";
		str = str + "\u0009Credit-Nr.:\u0009" + this.creditcard + "\n";
		str = str + "\u0009Gültig bis:\u0009" + this.month + " " + this.year + "\n";
				
		
		return str;
	}
	
	

}
