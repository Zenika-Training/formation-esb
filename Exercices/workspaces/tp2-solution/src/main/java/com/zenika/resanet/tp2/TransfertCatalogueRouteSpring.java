package com.zenika.resanet.tp2;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransfertCatalogueRouteSpring extends RouteBuilder {

	@Value("${export.catalogue}")
	private String exportCatalogueUri;
	
	@Value("${import.web}")
	private String importWebUri;
	
	@Override
	public void configure() throws Exception {
		
		from(exportCatalogueUri)
		.to("direct:from");
		
		from("direct:from")
		.log("${body}")
		.to("direct:to");
		
		from("direct:to")
		.to(importWebUri);
		
	}

}
