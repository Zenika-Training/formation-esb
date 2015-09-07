package com.resanet.monitoring_validation_su.copy;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class Validation extends RouteBuilder {

	private final static String NAMESPACE = "http://www.resanet.com/informations";
	private final static String SERVICE_IN = "jbi:service:" + NAMESPACE
			+ "/validation";
	private final static String SERVICE_OUT = "jbi:service:" + NAMESPACE
			+ "/traitement";

	@Override
	public void configure() throws Exception {
		// source
		from(SERVICE_IN)
		
			// traitement
			.process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				System.out.println("\n###### DEBUT VALIDATION ######");
				System.out.println(exchange.getIn().getBody());
				System.out.println("###### FIN VALIDATION   ######");
			}

		})
		
		// destination
		.to(SERVICE_OUT);
	}
}
