package com.resanet.eip.camel.transformations;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MessageTranslator extends RouteBuilder {

	private static final String LOG = "log:" + MessageTranslator.class + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// processor message translator
		from("direct:msgTransProc")
			.process(new WorldProcessor())
			.to("direct:messageTranslatorOut");

		// transform message translator
		from("direct:msgTransTransf")
			.transform(body().append(" Transform!"))
			.to("direct:messageTranslatorOut");

		
		from("direct:messageTranslatorOut").to(LOG);
	}
	
	static class WorldProcessor implements Processor {
		public void process(Exchange exchange) {
			Message in = exchange.getIn();
			in.setBody(in.getBody(String.class) + " World!");
		}
	}
}
