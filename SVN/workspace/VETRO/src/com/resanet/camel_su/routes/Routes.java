package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;


public class Routes extends RouteBuilder {

	private static final String LOG = "log:" + Routes.class + "?showAll=true&multiline=true";

	@Autowired
	private CatalogueWrapper catalogue;
	
	@Override
	public void configure() throws Exception {

		// point d'entrée JBI
		from("jbi:endpoint:http://esb.resanet.com/CamelVETRO/WebResa")
		.to(LOG)
		
		// validation syntaxique
		.to("validator:requeteWeb.xsd")
		.to(LOG)
		
		// enrichissement via le catalogue
		// et transformation vers le format cible
		.bean(catalogue,"enrichirEtTransformer")
		.to(LOG)
		
		// routage vers le service destination
		.to("jbi:endpoint:http://esb.resanet.com/jmsProviderTPVETRO/jmsProvider");
	}
}
