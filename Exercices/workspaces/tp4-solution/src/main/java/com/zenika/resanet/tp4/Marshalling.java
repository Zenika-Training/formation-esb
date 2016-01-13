package com.zenika.resanet.tp4;

import org.apache.camel.builder.RouteBuilder;

public class Marshalling extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("jms://queue:queue.resanet.reservation_vers_compta")
		    .unmarshal().jaxb("com.resanet.resa")
		    .bean(ReservationConverter.class, "toCSV")
		    .to("file://C:/resanet/importReservationCSVCompta?fileName=transac-${date:now:yyyy-MM-DD-HH-mm-SS-sss}.csv");
		
	}

}
