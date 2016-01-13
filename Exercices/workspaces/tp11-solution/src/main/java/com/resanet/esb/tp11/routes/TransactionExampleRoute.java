package com.resanet.esb.tp11.routes;

import org.apache.camel.builder.RouteBuilder;

public class TransactionExampleRoute extends RouteBuilder {

	private static final String LOG = "log:" + TransactionExampleRoute.class + "?showAll=true&multiline=true";

	@Override
	public void configure() {


        from("activemqTx:queue:transaction.incoming.one")
                .transacted("PROPAGATION_REQUIRED")
                .throwException(new Exception("Erreur de traitement !!!"))
                .to("activemqTx:queue:transaction.outgoing.one")
                .to(LOG);



	}
}
