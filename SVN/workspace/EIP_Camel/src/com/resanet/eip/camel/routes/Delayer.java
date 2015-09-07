package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class Delayer extends RouteBuilder {

	private static final String LOG = "log:" + Delayer.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {

		// délai statique
		from("seda:delayer")
			.delayer(1000)
			.to("seda:delayerOut");
		
		
		// délai dynamique
		from("seda:delayer2")
			.delay()
			.header("delai")
			.to("seda:delayerOut");
		
		
		from("seda:delayerOut").to(LOG);
	}
}
