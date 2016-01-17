package com.zenika.resanet.tp2;

import org.apache.camel.builder.RouteBuilder;

public class TransfertCatalogueRouteTest extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		// TODO 2 : fdqs
		from("file://C:/resanet/exportCatalogue")
		// TODO 3 : FIX
		.to("file://C:/resanet/importWeb");
		
	}

}
