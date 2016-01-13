package com.zenika.resanet.tp6.beans;

import org.apache.camel.Header;

public class DestinationChooser {

	private String destinationReponsePaire;

	private String destinationReponseImpaire;

	/**
	 * Methode qui choisit la destination JMS selon la parite de l'id de
	 * correlation.
	 * 
	 * @param headers les headers de l'echange
	 * @param correlationID le correlation ID
	 */
	public String isPair(@Header("correlation_id") int id) {
		if (id%2 == 0) return destinationReponsePaire;
		else
			return destinationReponseImpaire;
	}

	public void setDestinationReponsePaire(String destinationReponsePaire) {
		this.destinationReponsePaire = destinationReponsePaire;
	}

	public void setDestinationReponseImpaire(String destinationReponseImpaire) {
		this.destinationReponseImpaire = destinationReponseImpaire;
	}
}
