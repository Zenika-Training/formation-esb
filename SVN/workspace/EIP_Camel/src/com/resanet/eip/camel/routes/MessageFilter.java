package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class MessageFilter extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		from("seda:filter")
			.filter(header("orderType").isEqualTo("gadget"))
			.to("seda:filterOut");
	}
}
