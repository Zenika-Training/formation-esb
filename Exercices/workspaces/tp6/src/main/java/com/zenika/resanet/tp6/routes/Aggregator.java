package com.zenika.resanet.tp6.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class Aggregator extends RouteBuilder {

	private static final String LOG = "log:" + Aggregator.class.getName() + "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		from("direct:aggregator")
		.to(LOG)
		.aggregate(header("JMSCorrelationID"),
                new MergeAggregationStrategy())
                .completionTimeout(2000L).ignoreInvalidCorrelationKeys()
		.to(LOG)
        .loadBalance().roundRobin()
        .to("jms:queue:paire", "jms:queue:impaire");
	}

	/**
	 * Strategie d'aggregation qui fusionne tous les messages recus.
	 * 
	 */
	static public class MergeAggregationStrategy implements AggregationStrategy {
		public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
			if(oldExchange == null){
				StringBuilder sb = new StringBuilder();
				sb.append("<reponse xmlns=\"http://esb.resanet.com\">\n");
				sb.append(newExchange.getIn().getBody(String.class));
				newExchange.getIn().setBody(sb.toString(), String.class);
				return newExchange;
			}

			StringBuilder sb = new StringBuilder();
			sb.append(oldExchange.getIn().getBody(String.class));
			sb.append(newExchange.getIn().getBody(String.class));

			int aggSize = (Integer)oldExchange.getProperty(Exchange.AGGREGATED_SIZE);
			int nbMessages = (Integer)oldExchange.getProperty(Exchange.SPLIT_SIZE);
			if(aggSize == nbMessages - 1){
				sb.append("\n</reponse>");
			}

			newExchange.getIn().setBody(sb.toString(), String.class);
			return newExchange;
		}
	}
}
