package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;

public class TP5c extends RouteBuilder {

	private static final String LOG = TP5c.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// routage statique séquentiel
		from("direct:mutlicast")
		.convertBodyTo(String.class)
		.multicast().parallelProcessing()
		.to("log:DEST1_" + LOG, "log:DEST2_" + LOG,	"log:DEST3_" + LOG);

	}
}
