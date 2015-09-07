package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;

public class TP5b extends RouteBuilder {

	private static final String LOG = TP5b.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {

		from("jbi:endpoint:http://esb.resanet.com//CamelCBR/cbr")
		.convertBodyTo(String.class)
		.to("log:" + LOG)
		.choice()
			.when(header("typeMessage").isEqualTo("gadget"))
				.to("log:" + TP5b.class + ".GADGET?showAll=true&multiline=true")
				.to("jbi:endpoint:http://esb.resanet.com//jmsProviderTP5b1/jmsProviderGadget")

			.when(header("typeMessage").isEqualTo("widget"))
				.to("log:" + TP5b.class + ".WIDGET?showAll=true&multiline=true")
				.to("jbi:endpoint:http://esb.resanet.com//jmsProviderTP5b2/jmsProviderWidget")

			.otherwise()
				.to("log:" + TP5b.class + ".AUTRE?showAll=true&multiline=true")
		.end();
		
	}
}
