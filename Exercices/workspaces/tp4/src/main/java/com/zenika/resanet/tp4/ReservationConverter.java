package com.zenika.resanet.tp4;

import org.apache.camel.Body;

import com.resanet.resa.Reservation;
import com.resanet.resa.Reservation.Resa;

public class ReservationConverter {

	public String toCSV(@Body Reservation reservation) {
		StringBuilder sb = new StringBuilder("produit;date;quantite");
		sb.append(System.getProperty("line.separator"));
		for (Resa r : reservation.getResa()) {
			sb.append(r.getProduit());
			sb.append(";");
			sb.append(r.getDate());
			sb.append(";");
			sb.append(r.getQuantite());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}
