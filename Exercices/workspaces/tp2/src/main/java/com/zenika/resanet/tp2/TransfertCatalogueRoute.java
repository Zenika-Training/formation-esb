package com.zenika.resanet.tp2;

import org.apache.camel.builder.RouteBuilder;

public class TransfertCatalogueRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("file://C:/resanet/exportCatalogue")
		.to("file://C:/resanet/importWeb");
		
	}

}
