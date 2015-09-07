package com.resanet.camel_su.routes;

import org.apache.camel.Property;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;
import org.apache.camel.language.bean.BeanExpression;

public class Splitter extends RouteBuilder {

	private static final String LOG = "log:" + Splitter.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		XPathBuilder xpathChildRequete = new XPathBuilder("//esb:requete/child::*");
		xpathChildRequete.namespace("esb", "http://esb.resanet.com");

		from("direct:split")
			.to(LOG)
			.split(xpathChildRequete)
			.to(LOG)
			.recipientList(new BeanExpression(new Splitter(), "listbean"))
			.to("direct:aggregator")
			.to(LOG);
	}


	public String listbean(@Property("CamelSplitIndex") String idx) {
		return "bean:bean" + idx;
	}
}
