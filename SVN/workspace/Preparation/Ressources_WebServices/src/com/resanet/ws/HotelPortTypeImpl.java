package com.resanet.ws;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import com.resanet.common.Hotel;
import com.resanet.common.RechercherHotelFault;

@WebService(endpointInterface = "com.resanet.ws.HotelPortType", targetNamespace = "http://www.resanet.partenaires.com/hotel", serviceName = "partenaireService", portName = "partenaireServicePort")
public class HotelPortTypeImpl implements HotelPortType {

	// base de donnees hotels
	private static final Map<String, Hotel> HOTELS_DB = new HashMap<String, Hotel>();
	static {
		Hotel hotel = new Hotel();
		hotel.setNom("HOTEL TOP");
		hotel.setNbEtoiles(2);
		hotel.setVille("Lyon");
		HOTELS_DB.put("EXT0001", hotel);

		hotel = new Hotel();
		hotel.setNom("HOTEL MEGA");
		hotel.setNbEtoiles(5);
		hotel.setVille("Paris");
		HOTELS_DB.put("EXT0002", hotel);
	}


	public Hotel trouverHotel(String reference) throws RechercherHotelFault {
		System.out.println("[LOG WS] - reference = " + reference);
		
		Hotel hotel = HOTELS_DB.get(reference);
		if (hotel == null) {
			RechercherHotelFault exception = new RechercherHotelFault(
					"Erreur de recherche", "Hotel inconnu");
			throw exception;
		}
		return hotel;
	}

}
