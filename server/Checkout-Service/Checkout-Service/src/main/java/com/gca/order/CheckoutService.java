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
	 * [ ] Gleiche alle Produkte aus dem Cart mit dem Catalog ab (jedes Produkt einzeln) + werfe falsche Werte raus
	 * [ ] Frage ShippingKosten nach
	 * 
	 * [ ] Warte auf Kaufbestätigung
	 * 
	 * [ ] Frage nach Trackingnummer
	 * [ ] Speichere Trackingnummer in Order-Repository mit Cart, Email, usw.
	 * [ ] Gibt Order-Informationen aus.
	 */
	
	private final OkHttpClient httpClient = new OkHttpClient();
	
	public void checkout() {
		List<Product>cart = new ArrayList<Product>();
		List<Product>actualCart = new ArrayList<Product>();
		double productCosts;
		double shippingCosts;
		
		//Schritt 1: Cart auslesen (noch keine Validierung)
		cart = getCart();
		
		if(cart != null) {
			//Schritt 2: Cart mit Repository validieren
			actualCart = validateCart(cart);
		}
		
		if(actualCart != null) {
			//Schritt 3 und Versandkosten bestimmen
			productCosts = getProductCosts(actualCart);
			
		}
		
		
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
            System.out.println(jcode);
            
            
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
	
	private List<Product> validateCart(List<Product> falseCart) {
		List<Product> actualCart = new ArrayList<Product>();
		
		return actualCart;
	}
	
	private double getProductCosts(List<Product> cart) {
		double sum = 0;
		for(int i = 0; i < cart.size(); i++) {
			sum = sum + cart.get(i).price;
		}
		return sum;
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
