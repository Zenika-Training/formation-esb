package com.zenika.resanet.tp6.routes;

import org.apache.camel.builder.RouteBuilder;

public class JMSAccessPoints extends RouteBuilder {

	private static final String LOG = "log:" + JMSAccessPoints.class.getName() + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// point d'entree
		from("jms:queue:queue/resanet/routageComplexe/requete")
		.to(LOG)
		.to("direct:cbr");

		// sortie
		from("direct:out")
		.to(LOG)
		.to("jms:queue:queue/resanet/tp61/reponseOut");

		// sortie notification
		from("direct:outNotification")
		.to(LOG)
		.to("jms:queue:queue/resanet/tp61/reponseNotification");
	}
}
