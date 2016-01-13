package com.zenika.resanet.tp5.transform;

import org.apache.camel.builder.RouteBuilder;

import java.util.Date;

public class TransformRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

        // TODO 4 : Ecrire une route qui affiche dans les logs le message de direct:transformRoute
        // TODO 5 : Transformer le message pour y ajouter la date du jour

        // transform message translator
        from("direct:transformRoute")
                .convertBodyTo(String.class)
                .transform(body().append(new Date()))
                .log("$body")
                .to("log:"+this.getClass().getName()+"?showAll=true&multiline=true");

    }

}
