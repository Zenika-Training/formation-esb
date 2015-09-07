package com.resanet.services;

import java.util.Random;

public class StockService {

	public Integer lireNiveau() {
		int stock = new Random().nextInt(100);
		System.out.println("[ETAT DU STOCK] : " + stock);
		return stock;
	}
	
	public void afficherAlerte(String body) {
		System.out.println("[!!!] ALERTE STOCK : " + body);
	}

}
