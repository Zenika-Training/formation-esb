package com.resanet.esb.tp8;

import com.resanet.esb.tp8.service.HotelRechercheService;
import org.apache.camel.builder.RouteBuilder;

public class RouterPojoWS extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("cxf:bean:routerEndpoint?synchronous=true")   // POJO mode   par default
		.to("log:WEBSERVICES?showAll=true&multiline=true")
		.choice()
                .when().simple("${in.body[0].reference} contains 'EXT'")   // body renvoie un MessageContentsList
					.to("cxf:bean:servicePartenaireEndpoint")

                .otherwise()
                    .bean(HotelRechercheService.class)  ;


    }


}
