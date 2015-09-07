package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;

public class Routes extends RouteBuilder {

	private static final String LOG = "log:" + Routes.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {
		from("jbi:endpoint:http://esb.resanet.com/CamelAuth/camelAuth").to(LOG);
	}
}
