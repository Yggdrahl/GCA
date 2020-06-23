package com.gca.shipping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ShippingService {
	
	//---------------------------------------------
	// Versandkosten
	//---------------------------------------------
	
	public double getShippingCosts(double costs) {
		
		if(costs < 100) {
			return 10;
		}
		return 0;
		
	}
	
	//---------------------------------------------
	// Trackingnummer (eigene Hashfunktion)
	//---------------------------------------------
	
	public List<String> trackingRepo = new ArrayList<String>();
	
	public String generateTracking() {
		String tmp;
		do {
			tmp = hashGen();
		} while(this.trackingRepo.contains(tmp));
		
		this.trackingRepo.add(tmp);
		
		return tmp;
	}
	
	
	
	private String hashGen() {
		return generateChars(1) +
				generateNumber(4) +
				generateChars(2) +
				generateNumber(1) +
				"-" +
				generateChars(1) +
				generateNumber(2) +
				generateChars(1) +
				"-" +
				generateNumber(1) +
				generateChars(2) +
				generateNumber(1) +
				"-" +
				generateChars(1) +
				generateNumber(2) +
				generateChars(1) +
				"-" +
				generateNumber(1) +
				generateChars(1) +
				generateNumber(4) +
				generateChars(2) +
				generateNumber(1) +
				generateChars(1) +
				generateNumber(2);
	}
	
	private String generateChars(int symbols) {
		String result = "";
		Random r = new Random();
		for(int i = 0; i < symbols; i++) {
			result = result + (char) (r.nextInt(26) + 'a');
		}
		return result;
	}
	
	private int generateNumber(int symbols) {
		
		if(symbols <= 0) {
			return 0;
		}
		
		String obergrenze = "9";
		String untergrenze = "1";
		
		for(int i = 1; i < symbols; i++) {
			obergrenze = obergrenze + 9;
			untergrenze = untergrenze + 0;
		}
		
		int obergrenzeInt = Integer.valueOf(obergrenze);
		int untergrenzeInt = Integer.valueOf(untergrenze);
		
		
		return (int) (Math.random() * (obergrenzeInt - untergrenzeInt + 1) + untergrenzeInt);
	}

}
