package com.zenika.resanet.tp6.routes;

import org.apache.camel.builder.RouteBuilder;

public class ContentBasedRouter extends RouteBuilder {
	
	private static final String LOG = "log:" + ContentBasedRouter.class.getName() + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {
		
		from("direct:cbr")
		.to(LOG)
		.choice()
			.when(xpath("/esb:requete").namespace("esb", "http://esb.resanet.com"))
				.to(LOG)
				.to("direct:split")
			.otherwise()
				.to("direct:outNotification");
	}
	
}
