package com.resanet.esb.tp12.wiretap;

import org.apache.camel.builder.RouteBuilder;

public class Wiretap extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// source
		from("jms:queue:in_tp12")
                .wireTap("jms:queue:wiretap")
                .to("jms:queue:out_tp12");
	}
}
