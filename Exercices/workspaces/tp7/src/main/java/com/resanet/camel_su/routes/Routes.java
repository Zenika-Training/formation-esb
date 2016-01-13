package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;
import org.springframework.beans.factory.annotation.Autowired;


public class Routes extends RouteBuilder {

	private static final String LOG = "log:" + Routes.class.getName() + "?showAll=true&multiline=true";

	@Autowired
	private CatalogueWrapper catalogueWrapper;

	@Override
	public void configure() throws Exception {

		// point d'entree JMS
		from("jms:queue/resanet/web_vers_esb")
		.to(LOG)
		
		
		// validation syntaxique
		.to("validator:requeteWeb.xsd")
		.to(LOG)
		
		// enrichissement via le catalogue
		// et transformation vers le format cible
		.bean(catalogueWrapper,"enrichirEtTransformer")
		.convertBodyTo(String.class)
		.to(LOG)
		
		// routage vers le service destination
		.to("jms:queue/resanet/esb_vers_resa");
	}

    public static void main(String[] args) throws Exception {
        Main.main(args);
    }
}
