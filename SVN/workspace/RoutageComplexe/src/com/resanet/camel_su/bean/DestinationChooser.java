package com.resanet.camel_su.bean;

import java.util.Map;

import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.commons.logging.LogFactory;

public class DestinationChooser {

	private String destinationReponsePaire;

	private String destinationReponseImpaire;

	private static final String SMX_JMS_DESTINATION = "org.apache.servicemix.jms.destination";

	/**
	 * Méthode qui choisit la destination JMS selon la parité de l'id de
	 * correlation.
	 * 
	 * @param headers les headers de l'échange
	 * @param correlationID le correlation ID
	 */
	public void choose(@Headers Map<String, Object> headers, @Header(value = "correlation_id") String correlationID) {

		LogFactory.getLog(DestinationChooser.class).info("correlation_id recu " + correlationID);

		if (Long.valueOf(correlationID) % 2 == 0) {
			headers.put(SMX_JMS_DESTINATION, destinationReponsePaire);
		} else {
			headers.put(SMX_JMS_DESTINATION, destinationReponseImpaire);
		}
	}

	public void setDestinationReponsePaire(String destinationReponsePaire) {
		this.destinationReponsePaire = destinationReponsePaire;
	}

	public void setDestinationReponseImpaire(String destinationReponseImpaire) {
		this.destinationReponseImpaire = destinationReponseImpaire;
	}
}
