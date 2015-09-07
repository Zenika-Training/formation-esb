package com.resanet.esb.tp8.service;

import com.partenaires.resanet.hotel.Hotel;
import com.partenaires.resanet.hotel.RechercherHotelFault;
import com.partenaires.resanet.hotel.RechercherHotelRequest;
import com.partenaires.resanet.hotel.RechercherHotelResponse;
import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HotelRechercheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelRechercheService.class);

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

    public RechercherHotelResponse rechercherHotel(@Body RechercherHotelRequest request) throws RechercherHotelFault {
        String reference = request.getReference();
        LOGGER.info("Reference : {}", reference);
        Hotel hotel = HOTELS_DB.get(reference);
        LOGGER.info("hotel : {}", hotel);
        if (hotel == null) {
            throw new RechercherHotelFault("Erreur de recherche", "Hotel inconnu");
        }

        RechercherHotelResponse response = new RechercherHotelResponse();
        response.setHotel(hotel);

        return response;
    }
}
