package com.zenika.resanet.tp5.cbr;

import org.apache.camel.builder.RouteBuilder;

public class ContentBasedRoute extends RouteBuilder {

	private static final String LOG = ContentBasedRoute.class.getName() + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {

		from("direct:cbr")
		.to("log:" + LOG)
		.choice()
			.when(header("MESSAGE_TYPE").isEqualTo("reservation"))
                .log("C'est une Reservation !")

			.when(header("MESSAGE_TYPE").isEqualTo("promotion"))
                .log("C'est une Promotion !")

			.otherwise()
                .log("C'est autre chose !")
                .to("log:" + ContentBasedRoute.class.getName() + ".OTHERS?showAll=true&multiline=true")
		.end();
		
	}
}
