package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;

public class Splitter extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		Namespaces ns = new Namespaces("esb","http://esb.resanet.com");
		from("seda:splitterXPath")
			.split(ns.xpath("//esb:order/esb:commande"))
			.to("seda:splitterXPathOut");
		
		from("seda:splitterRegex")
			.split(body().regexTokenize("d.f"))
			.to("seda:splitterRegexOut");
		
	}
}
