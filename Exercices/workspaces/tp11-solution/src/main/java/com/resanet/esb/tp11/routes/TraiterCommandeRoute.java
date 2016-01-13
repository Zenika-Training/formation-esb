package com.resanet.esb.tp11.routes;

import org.apache.camel.spring.SpringRouteBuilder;

import com.resanet.esb.tp11.services.CommandeService;

public class TraiterCommandeRoute extends SpringRouteBuilder {

	private static final String LOG = "log:" + TraiterCommandeRoute.class + "?showAll=true&multiline=true";

	@Override
	public void configure() {

        from("jetty:http://localhost:8095/commande")
                .to(LOG)
                .inOnly("jmstx:queue:commande_recue")
                .transform(constant("Votre commande est en cours de traitement !"));


        from("jmstx:queue:commande_recue")
        	.to(LOG)
        	.bean(CommandeService.class, "receptionCommande")
        	.to("jmstx:queue:commande_traitee");


	}
}
