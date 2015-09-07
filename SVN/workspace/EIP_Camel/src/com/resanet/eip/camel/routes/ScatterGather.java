package com.resanet.eip.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Service;

@Service
public class ScatterGather extends RouteBuilder {
	
	private static final String LOG = "log:" +  ScatterGather.class + "?showAll=true&multiline=true";
	

	@Override
	public void configure() throws Exception {
	
		// Multicast / RecipientList
		from("direct:scattergather")
			.recipientList(header("catalogue"));

		// Aggregator
		AggregationStrategy  as = new ConcatAggregationStrategy();
		from("direct:sgAgg")
			.aggregate(as)
			.header("correlation.id")
			.to("direct:sgOut");
		
		from("direct:sgOut").to(LOG);
	}
	
	
	/**
	 * Algorithme de concaténation des bodies uniquement.
	 *
	 */
	public class ConcatAggregationStrategy implements AggregationStrategy{
		
		public Exchange aggregate(Exchange oldEx, Exchange newEx) {
			
			StringBuilder sb = new StringBuilder();
			sb.append(oldEx.getIn().getBody(String.class));
			sb.append("!!!!");
			sb.append(newEx.getIn().getBody(String.class));
			newEx.getIn().setBody(sb.toString(), String.class);
			
			return newEx;
		}
	}
}
