package com.resanet.monitoring_traitement_su;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class Traitement extends RouteBuilder {

	private final static String NAMESPACE = "http://www.resanet.com/informations";
	private final static String SERVICE_IN = "jbi:service:" + NAMESPACE
			+ "/traitement";
	private final static String SERVICE_OUT = "jbi:service:" + NAMESPACE
			+ "/jmsDestination";

	@Override
	public void configure() throws Exception {
		// source
		from(SERVICE_IN)
		
			// traitement
			.process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				System.out.println("\n|||||||||||| DEBUT TRAITEMENT ||||||||||||");
				System.out.println(exchange.getIn().getBody());
				System.out.println("|||||||||||| FIN TRAITEMENT   ||||||||||||");
			}

		})
		
		// destination
		.to(SERVICE_OUT);
	}
}
