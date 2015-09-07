package com.resanet.eip.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class Multicast extends RouteBuilder {
	
	private static final String LOG = "log:" + Multicast.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {
		
		// routage statique séquentiel
		from("seda:multicasts")
		.multicast()
		.to("direct:multicastOut","direct:multicastOut");
		
		// routage statique parallèle
		from("seda:multicastp")
		.multicast().parallelProcessing()
		.to("direct:multicastOut","direct:multicastOut");
		
		// routage statique avec AggregatorStrategy
		from("direct:multicastAggStg")
		.multicast(new BodyInAggregatingStrategy())
		.to("direct:processor","direct:processor");

		from("direct:processor").process(new AppendingProcessor());
		
		from("direct:multicastOut").to(LOG);
	}
	
	static public class BodyInAggregatingStrategy implements AggregationStrategy {
	    public Exchange aggregate(Exchange oldEx, Exchange newEx) {
	        if (oldEx != null) {
	            String oldBody = oldEx.getIn().getBody(String.class);
	            String newBody = newEx.getIn().getBody(String.class);
	            newEx.getOut().setBody(oldBody + "+" + newBody);
	        }
	        return newEx;
	    }
	}
	
	static public class AppendingProcessor implements Processor{
	    public void process(Exchange exchange) {
	        Message in = exchange.getIn();
	        String body = in.getBody(String.class);
	        in.setBody(body + "+AppendingProcessor  ");
	    }
	}
}
