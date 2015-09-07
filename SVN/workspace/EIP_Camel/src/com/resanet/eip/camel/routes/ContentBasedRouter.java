package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class ContentBasedRouter extends RouteBuilder {
	@Override
	public void configure() throws Exception {		
		from("seda:cbr")
		.choice()
			.when(header("orderType").isEqualTo("gadget"))
				.to("seda:gadgetInventory")

			.when(header("orderType").isEqualTo("widget"))
				.to("seda:widgetInventory")

			.otherwise()
				.to("seda:autre")
		.end();
	}
}
