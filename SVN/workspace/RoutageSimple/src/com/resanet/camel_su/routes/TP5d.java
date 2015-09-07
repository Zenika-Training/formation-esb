package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;

public class TP5d extends RouteBuilder {

	private static final String LOG = "log:" + TP5d.class
			+ "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// transform message translator
		 from("direct:messageTranslator")
		 .convertBodyTo(String.class)
		 .transform(body().append("World !"))
		 .to(LOG);


	}
}
