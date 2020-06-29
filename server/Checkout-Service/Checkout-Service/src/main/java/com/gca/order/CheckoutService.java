package com.gca.order;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import okhttp3.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;





@Service
public class CheckoutService {
	
	/*
	 * ToDo:
	 * [X] Hole Alles aus dem Cart raus.
	 * [X] Gleiche alle Produkte aus dem Cart mit dem Catalog ab (jedes Produkt einzeln) + werfe falsche Werte raus
	 * [X] Frage Shipping-Kosten nach
	 * 
	 * [X] Frage nach Trackingnummer
	 * [X] Speichere Trackingnummer in Order-Repository mit Cart, Email, usw.
	 * [X] Gibt Order-Informationen aus.
	 */
	
	
	final OkHttpClient httpClient = new OkHttpClient();
	
	private Logger LOG = LoggerFactory.getLogger(CheckoutController.class);
	
	public List<Order> orders = new ArrayList<Order>(); //Alle Bestellungen
	
	private String authPW="testpw";
	
	//private String ip = "localhost";
	//private String host = "172.18.11.241"; //Bitte stehen lassen
	
	
	public String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "localhost";
		}
	}

	public int checkout(Order order) {
		
		//String ip = getIp();
		
		if(order == null) {
			return -1;
		}
				
		List<Product>cart = new ArrayList<Product>();
		List<Product>actualCart = new ArrayList<Product>();
		double productCosts;
		double shippingCosts;
		String tracking;
		int ordernummer;
		
		
		//Schritt 1: Cart auslesen (noch keine Validierung)
		cart = getCart();
		
		if(cart != null) {
			//Schritt 2: Cart mit Repository validieren
			actualCart = validateCart(cart);
		}
		
		
		if(actualCart != null) {
			//Schritt 3 und Versandkosten bestimmen
			
			if(actualCart.size() <= 0) {
				LOG.error("Couldn't complete Checkout. Cart is empty or Cart-Service is down");
				return -1;
			}
			
			productCosts = getProductCosts(actualCart);
			shippingCosts = getShippingcosts(productCosts);
			tracking = getTracking();
			ordernummer = generateOrdernummer();
			order.setCart(actualCart);
			order.setShipping(shippingCosts);
			order.setTracking(tracking);
			order.setOrdernummer(ordernummer);
			orders.add(order);
			deleteCart();
			LOG.info("Checkout finished");
			return ordernummer;
			
			
		}
		LOG.error("Couldn't complete Checkout. Cart is empty or Cart-Service is down");
		return -1;
		
	}
	
	
	
	
	
	
	public List<Product> getCart() {
		
		List<Product> currentCart = new ArrayList<Product>();
		
		String ip = getIp();
		
		//---------------------------------------------------/*
		Request request = new Request.Builder()
				.url("http://cart:8084/cart")
                .addHeader("Content-Type", "application/json")  // add request headers
                .addHeader("Authorization", this.authPW)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String jcode = response.body().string();
            //System.out.println(jcode);
            
            
            JSONParser jsonParser = new JSONParser();
            try {
            	
				//JSONObject jsonObject = (JSONObject) jsonParser.parse(jcode);
				JSONArray jsonArray = (JSONArray) jsonParser.parse(jcode);
				for(int i = 0; i < jsonArray.size(); i++) {
					JSONObject obj = (JSONObject) jsonArray.get(i);
					
					currentCart.add(new Product(
							(int) (long) (obj.get("id")),
							(Double) obj.get("price"),
							(String) obj.get("name"),
							(String) obj.get("image")
							));
							
				}
				LOG.info("Products catched from cart-service. " + currentCart.size() + " Object(s) fetched from Microservice.");
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				LOG.error("The cart-service doesn't response correctly. Couldn't parse http.body (JSON) to Object");
				e.printStackTrace();
			}
            
        } catch (IOException e) {
			// TODO Auto-generated catch block
        	LOG.error("The cart-service doesn't respond. The http.body is empty");
			e.printStackTrace();
		}
		//---------------------------------------------------
		
		return currentCart;
	}
	
public List<Product> getCatalog() {
		
		List<Product> catalog = new ArrayList<Product>();
		
		String ip = getIp();
		
		//---------------------------------------------------
		Request request = new Request.Builder()
                .url("http://catalog:8081/products")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String jcode = response.body().string();           
            JSONParser jsonParser = new JSONParser();
            try {
				JSONArray jsonArray = (JSONArray) jsonParser.parse(jcode);
				for(int i = 0; i < jsonArray.size(); i++) {
					JSONObject obj = (JSONObject) jsonArray.get(i);
					catalog.add(new Product(
							(int) (long) (obj.get("id")),
							(Double) obj.get("price"),
							(String) obj.get("name"),
							(String) obj.get("image")
							));
				}
				LOG.info("Products catched from catalog-service. " + catalog.size() + " Object(s) fetched from Microservice.");
			} catch (ParseException e) {
				LOG.error("The catalog-service doesn't response correctly. Couldn't parse http.body (JSON) to Object");
				e.printStackTrace();
			}
            
        } catch (IOException e) {
        	LOG.error("The catalog-service doesn't respond. The http.body is empty");
			e.printStackTrace();
		}
		//---------------------------------------------------
		
		return catalog;
	}
	
	private List<Product> validateCart(List<Product> falseCart) {
		List<Product> actualCart = new ArrayList<Product>();
		List<Product> catalog = getCatalog();
		LOG.info("Starting to validate the current cart...");
		for(int x = 0; x < falseCart.size(); x++) {
			
			for(int y = 0; y < catalog.size(); y++) {
				if(catalog.get(y).getId() == falseCart.get(x).getId()) {
					actualCart.add(catalog.get(y));
				} else {
					//LOG.info("Product ID:" + catalog.get(y).getId() + " is incorrect or doesn't even exists");
				}
				
			}
		}
		LOG.info((falseCart.size()-actualCart.size()) + " Products had to be correctet");
		return actualCart;
	}
	
	private double getProductCosts(List<Product> cart) {
		double sum = 0;
		for(int i = 0; i < cart.size(); i++) {
			sum = sum + cart.get(i).price;
		}
		LOG.info("All products costs " + (sum/100)*100 + " (without shipping)");
		return sum;
	}
	
	private double getShippingcosts(double orderSum) {
	
		double result = 10;
		
		String ip = getIp();
		
		//------------------------------------------
		Request request = new Request.Builder()
                .url("http://shipping:8082/getShippingCosts/?costs=" + orderSum)
                .addHeader("Authorization", this.authPW)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            result = Double.valueOf((response.body().string()));          
            
            
        } catch (IOException e) {
        	LOG.error("The shipping-service doesn't respond or the response is incorrect. The shippingcosts couldn't be checked.");
			e.printStackTrace();
		}
        //---------------------------------
		
		return result;
	}
	
	private String getTracking() {
		
		String result = "0001";
		//------------------------------------------
		Request request = new Request.Builder()
                .url("http://shipping:8082/getTracking")
                .addHeader("Authorization", this.authPW)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            result = response.body().string();          
            
            
        } catch (IOException e) {
        	LOG.error("The shipping-service doesn't respond or the response is incorrect. The tracking-number couldn't be generatet.");
			e.printStackTrace();
		}
        //---------------------------------
		
		return result;
	}
	
	private int generateOrdernummer() {
		
		int result;
		boolean exists = false;
		do {
			exists = false;
			result  = (int) (Math.random() * (1000000 - 100 + 1) + 1000000);
			for(int i = 0; i < orders.size(); i++) {
				if(orders.get(i).getOrdernummer() == result) {
					exists = true;
				}
			}
		} while(exists);
		LOG.info("The unique ordernumber " + result + " was createt and saved");
		return result;
	}
	
	public Order getOrder(int ordNumber) {
		
		for(int i = 0; i < this.orders.size(); i++) {
			if(this.orders.get(i).getOrdernummer() == ordNumber) {
				return this.orders.get(i);
			}
		}
		LOG.error("No such order with the order-number " + ordNumber);
		return null;
	}
	
	public void deleteCart() {
		String ip = getIp();
		//------------------------------------------
				Request request = new Request.Builder()
		                .url("http://cart:8084/emptyCart")
		                .addHeader("Authorization", this.authPW)
		                .delete()
		                .build();

		        try (Response response = httpClient.newCall(request).execute()) {
	            
		        } catch (IOException e) {
		        	LOG.error("Couldn't delte cart");
					e.printStackTrace();
				}
		        //---------------------------------
	}
	
	
	//Resiliennce
	
	
	
	
		/*
		// form parameters
        RequestBody formBody = new FormBody.Builder()
                .add("username", "abc")
                .add("password", "123")
                .add("custom", "secret")
                .build();

        Request request = new Request.Builder()
                .url("https://httpbin.org/post")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(formBody)
                .build();
                
        */
	
	
	
	
	
	
	

}
