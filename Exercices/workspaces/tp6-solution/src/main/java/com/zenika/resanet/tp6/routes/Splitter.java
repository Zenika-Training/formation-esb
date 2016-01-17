package com.zenika.resanet.tp6.routes;

import org.apache.camel.ExchangeProperty;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.bean.BeanExpression;

public class Splitter extends RouteBuilder {

	private static final String LOG = "log:" + Splitter.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		from("direct:split")
			.to(LOG)
			.split(xpath("//esb:requete/child::*").namespace("esb", "http://esb.resanet.com"))
			.recipientList(new BeanExpression(new Splitter(), "listbean"))
			.to("direct:aggregator")
			.to(LOG);
	}


	public String listbean(@ExchangeProperty("CamelSplitIndex") String idx) {
		return "bean:bean" + idx;
	}
	
}
