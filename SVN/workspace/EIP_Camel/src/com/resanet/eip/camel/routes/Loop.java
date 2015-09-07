package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class Loop extends RouteBuilder {
	
	private static final String LOG = "log:" + Loop.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {
		
		// from("seda:loop").loop(3).to("seda:loopOut");
		
		// répétition dynamique
		from("seda:loop")
			.loop().xpath("/body/nbOrders")
			.to("seda:loopOut");

		from("seda:loopOut").to(LOG);
	}
}
