package com.resanet.ws;

import javax.xml.ws.Endpoint;

public class ServerPartenaire {

	public static void main(String[] args) {
		System.out.println("Serveur d�marr�...");
		
		HotelPortType service = new HotelPortTypeImpl();
		String address = "http://localhost:11000/partenaire";
		Endpoint.publish(address, service);
	}
	
}
