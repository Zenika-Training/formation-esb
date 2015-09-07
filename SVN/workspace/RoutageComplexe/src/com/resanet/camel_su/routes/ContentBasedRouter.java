package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;

public class ContentBasedRouter extends RouteBuilder {
	
	private static final String LOG = "log:" + ContentBasedRouter.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {
		
		XPathBuilder xpathRequete = new XPathBuilder("/esb:requete");
		xpathRequete.namespace("esb", "http://esb.resanet.com");
		
		from("direct:cbr")
		.to(LOG)
		.choice()
			.when(xpathRequete)
				.to(LOG)
				.to("direct:split")
			.otherwise()
				.to("direct:outNotification");
	}
}
