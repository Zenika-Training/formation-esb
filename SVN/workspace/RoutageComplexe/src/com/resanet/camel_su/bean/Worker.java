package com.resanet.camel_su.bean;

public interface Worker {

	/**
	 * Ajoute un header et remplace le corps du message.
	 * 
	 * @param body le corps du message
	 * @param exchange l'echange de messages
	 * 
	 * @return le nouveau corps du message
	 */
	String work(String body, org.apache.camel.Exchange exchange);

}
