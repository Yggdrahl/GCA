package com.gca.order;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;



import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
	private Product[] cart;
	
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
					System.out.println(obj.get("id"));					
					currentCart.add(new Product(
							(int) obj.get("id"),
							(double) obj.get("price"),
							(String) obj.get("name"),
							(String) obj.get("image")
							));
				}
				/*
				JSONArray jsonArray = (JSONArray) jsonObject.get("id");
				Iterator<Integer> it = jsonArray.iterator();
				while(it.hasNext()) {
					System.out.println("id: " + it.next());
				}
				*/
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//---------------------------------------------------
		


		return null;
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
