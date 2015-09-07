package com.resanet.ws_se_su;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.xml.ws.Holder;

import com.resanet.common.Hotel;
import com.resanet.common.RechercherHotelFault;

@WebService(endpointInterface = "com.resanet.ws_se_su.HotelPortType", targetNamespace = "http://www.resanet.partenaires.com/hotel", serviceName = "hotelService", portName = "hotelServicePort")
public class HotelPortTypeImpl implements HotelPortType {

	// base de donnees hotels
	private static final Map<String, Hotel> HOTELS_DB = new HashMap<String, Hotel>();
	static {
		Hotel hotel = new Hotel();
		hotel.setNom("HOTEL CLASS'");
		hotel.setNbEtoiles(4);
		hotel.setVille("Rennes");
		HOTELS_DB.put("REF0001", hotel);

		hotel = new Hotel();
		hotel.setNom("HOTEL DELUXE");
		hotel.setNbEtoiles(3);
		hotel.setVille("Paris");
		HOTELS_DB.put("REF0002", hotel);
	}


	public Hotel rechercherHotel(String reference, Holder<String> parameters)
			throws RechercherHotelFault {
		Hotel hotel = HOTELS_DB.get(reference);
		if (hotel == null) {
			RechercherHotelFault exception = new RechercherHotelFault(
					"Erreur de recherche", "Hotel inconnu");
			throw exception;
		}
		parameters.value = "INTERNE";
		return hotel;
	}

}
