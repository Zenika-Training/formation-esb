package com.zenika.resanet.tp2;

import org.apache.camel.builder.RouteBuilder;

public class TransfertCatalogueRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:{{source.path}}")
		.to("file:{{target.path}}");
		
	}

}
