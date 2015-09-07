package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class Throttler extends RouteBuilder {

	private static final String LOG = "log:" + Throttler.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		from("seda:throttler")
			.throttle(10).timePeriodMillis(2000L)
			.to("seda:throttlerOut");
		
		from("seda:throttlerOut").to(LOG);
	}
}
