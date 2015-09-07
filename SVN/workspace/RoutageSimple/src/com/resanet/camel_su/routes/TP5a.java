package com.resanet.camel_su.routes;

import org.apache.camel.builder.RouteBuilder;

public class TP5a extends RouteBuilder {

	private static final String LOG = "log:" + TP5a.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {
		from("jbi:endpoint:http://esb.resanet.com//CamelRouter/pipe")
		.convertBodyTo(String.class).to(LOG)
		.to("jbi:endpoint:http://esb.resanet.com//fileSenderTP5a/sender");
	}
}
