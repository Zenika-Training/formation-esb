package com.resanet.eip.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class Aggregator extends RouteBuilder {
	
	private static final String LOG = "log:" +  Aggregator.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {
		
		from("seda:aggregator")
			.aggregate(header("correlation.id"), new CSVAggregationStrategy())
			.batchTimeout(2000L).batchSize(3)
			.to("seda:aggregatorOut");

		
		from("seda:aggregator2")
			.aggregate(header("correlation.id"), new CSVAggregationStrategy())
			.completionPredicate(header(Exchange.AGGREGATED_SIZE).isEqualTo(4))
			.to("seda:aggregatorOut");
		
		
		from("seda:aggregator3")
			.aggregate(new CSVAggregationStrategy())
			.xpath("/body", String.class).batchSize(4)
			.to("seda:aggregatorOut");

		
		from("seda:aggregatorOut").to(LOG);
	}
	
	public class CSVAggregationStrategy implements AggregationStrategy{
		
		public Exchange aggregate(Exchange oldEx, Exchange newEx) {
			
			StringBuilder sb = new StringBuilder();
			sb.append(oldEx.getIn().getBody(String.class));
			sb.append(";");
			sb.append(newEx.getIn().getBody(String.class));
			newEx.getIn().setBody(sb.toString(), String.class);
			
			return newEx;
		}
	}
}
