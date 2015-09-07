package com.resanet.eip.camel.routes;

import org.apache.camel.Body;
import org.apache.camel.builder.RouteBuilder;

public class RoutingSlip extends RouteBuilder {

	private static final String LOG = "log:" + RoutingSlip.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// Headers:{route=direct:rs1,direct:rs2,direct:rs3}
		from("seda:routingslip")
			.routingSlip("route");

		ChangeBody cb = new ChangeBody();
		from("direct:rs1").bean(cb);
		from("direct:rs2").bean(cb);
		from("direct:rs3").bean(cb).to(LOG);

	}

	/**
	 * Simple bean qui change le contenu du body.
	 */
	static public class ChangeBody {
		public String change(@Body String body){
			return body + body;
		}
	}
}
