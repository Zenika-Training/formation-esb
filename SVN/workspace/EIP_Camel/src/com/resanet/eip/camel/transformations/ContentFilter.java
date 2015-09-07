package com.resanet.eip.camel.transformations;

import org.apache.camel.builder.RouteBuilder;

public class ContentFilter extends RouteBuilder {

	private static final String LOG = "log:" + ContentFilter.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// XSLT content filter
		from("direct:contentFilter")
			.to("xslt:com/resanet/eip/camel/transformations/Contentfilter.xsl")
			.to("direct:contentEnricherOut");

		
		from("direct:contentFilterOut").to(LOG);
	}
}
