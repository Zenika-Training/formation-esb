package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;

public class JBIAccessPoints extends RouteBuilder {

	private static final String LOG = "log:" + JBIAccessPoints.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// point d'entrée
		from("jbi:endpoint:http://esb.resanet.com/RoutageComplexe/Camel")
		.to(LOG)
		.to("direct:cbr");

		// sortie
		from("direct:out")
		.to(LOG)
		.to("jbi:endpoint:http://esb.resanet.com/jmsProviderRoutageComplexe/out");

		// sortie notification
		from("direct:outNotification")
		.to(LOG)
		.to("jbi:endpoint:http://esb.resanet.com/jmsProviderRoutageComplexe/outNotification");
	}
}
