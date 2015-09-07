package com.resanet.camel;

import org.apache.camel.builder.RouteBuilder;

public class Router extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// recuperation niveau stock
		from("timer://timerStock?fixedRate=true&period=5000")
			.beanRef("stockService", "lireNiveau")
		.to("nmr:stock");
		
		// controle niveau stock
		from("nmr:stock")
			.filter(body().isLessThan(50))
		.beanRef("stockService", "afficherAlerte");
	}

}
