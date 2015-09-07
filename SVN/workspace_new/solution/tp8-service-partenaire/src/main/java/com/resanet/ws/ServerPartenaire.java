package com.resanet.ws;

import com.partenaires.resanet.hotel.HotelEndPoint;

import javax.xml.ws.Endpoint;

public class ServerPartenaire {

	public static void main(String[] args) {
		System.out.println("Serveur demarre...");

        HotelEndPoint service = new HotelPortTypeImpl();
		String address = "http://localhost:11000/partenaire";
		Endpoint.publish(address, service);
	}
	
}
