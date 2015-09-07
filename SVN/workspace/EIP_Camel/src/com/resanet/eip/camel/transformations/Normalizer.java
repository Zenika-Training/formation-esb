package com.resanet.eip.camel.transformations;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;

public class Normalizer extends RouteBuilder {

	private static final String LOG = "log:" + Normalizer.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		Namespaces nsA = new Namespaces("a","http://esb.resanet.com/A");
		Namespaces nsB = new Namespaces("b","http://esb.resanet.com/B");
		
		from("direct:normalizer")
		.choice()
			.when(nsA.xpath("/a:formatA"))
				.to("xslt:com/resanet/eip/camel/transformations/NormalizerAtoFinal.xsl")
			
			.when(nsB.xpath("/b:formatB"))
				.to("xslt:com/resanet/eip/camel/transformations/NormalizerBtoFinal.xsl")
			
			.end()
		.to("direct:normalizerOut");

		from("direct:normalizerOut").to(LOG);
	}
}