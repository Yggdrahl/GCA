package com.gca.order;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;



import okhttp3.*;

import java.io.IOException;
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
	
	private final OkHttpClient httpClient = new OkHttpClient();
	
	public List<Order> orders = new ArrayList<Order>(); //Alle Bestellungen
	
	public int checkout(Order order) {
		
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
			productCosts = getProductCosts(actualCart);
			shippingCosts = getShippingcosts(productCosts);
			tracking = getTracking();
			ordernummer = generateOrdernummer();
			order.setCart(actualCart);
			order.setShipping(shippingCosts);
			order.setTracking(tracking);
			order.setOrdernummer(ordernummer);
			
			return ordernummer;
			
			
		}
		
		return -1;
		
	}
	
	
	
	
	
	
	public List<Product> getCart() {
		
		List<Product> currentCart = new ArrayList<Product>();
		//---------------------------------------------------
		Request request = new Request.Builder()
                .url("http://localhost:8084/cart")
                //.addHeader("Content-Type", "application/json")  // add request headers
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
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//---------------------------------------------------
		
		return currentCart;
	}
	
public List<Product> getCatalog() {
		
		List<Product> catalog = new ArrayList<Product>();
		//---------------------------------------------------
		Request request = new Request.Builder()
                .url("http://localhost:8081/products")
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
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
        } catch (IOException e) {
			e.printStackTrace();
		}
		//---------------------------------------------------
		
		return catalog;
	}
	
	private List<Product> validateCart(List<Product> falseCart) {
		List<Product> actualCart = new ArrayList<Product>();
		List<Product> catalog = getCatalog();
		
		for(int x = 0; x < falseCart.size(); x++) {
			
			for(int y = 0; y < catalog.size(); y++) {
				if(catalog.get(y).getId() == falseCart.get(x).getId()) {
					actualCart.add(catalog.get(y));
				}
			}
		}
		
		return actualCart;
	}
	
	private double getProductCosts(List<Product> cart) {
		double sum = 0;
		for(int i = 0; i < cart.size(); i++) {
			sum = sum + cart.get(i).price;
		}
		return sum;
	}
	
	private double getShippingcosts(double orderSum) {
	
		double result = 10;
		//------------------------------------------
		Request request = new Request.Builder()
                .url("http://localhost:8082/getShippingCosts/?costs=" + orderSum)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            result = Double.valueOf((response.body().string()));          
            
            
        } catch (IOException e) {
			e.printStackTrace();
		}
        //---------------------------------
		
		return result;
	}
	
	private String getTracking() {
		
		String result = "0001";
		//------------------------------------------
		Request request = new Request.Builder()
                .url("http://localhost:8082/getTracking")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            result = response.body().string();          
            
            
        } catch (IOException e) {
			e.printStackTrace();
		}
        //---------------------------------
		
		return result;
	}
	
	private int generateOrdernummer() {
		
		int result;
		boolean exists = false;
		do {
			result  = (int) (Math.random() * (1000000 - 100 + 1) + 1000000);
			for(int i = 0; i < orders.size(); i++) {
				if(orders.get(i).getOrdernummer() == result) {
					exists = true;
				}
			}
		} while(exists);
		
		return result;
	}
	
	
	
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
