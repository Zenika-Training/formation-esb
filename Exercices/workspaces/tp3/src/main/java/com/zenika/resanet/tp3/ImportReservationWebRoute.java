package com.zenika.resanet.tp3;

import org.apache.camel.builder.RouteBuilder;

public class ImportReservationWebRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("jms:queue:queue.resanet.reservation_vers_compta")
		.to("file:C:/resanet/importReservationCompta");
		
	}

}
