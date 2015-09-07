package com.resanet.eip.camel.transformations;

import java.util.Date;

import org.apache.camel.Body;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class ContentEnricher extends RouteBuilder {

	private static final String LOG = "log:" + ContentEnricher.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// processor content enricher
		from("direct:contentEnricher")
			.beanRef("contentEnricher", "ajouterDate")
			.to("direct:contentEnricherOut");

		
		from("direct:contentEnricherOut").to(LOG);
	}
	
	public String ajouterDate(@Body String body){
		return body + new Date();
	}
}
