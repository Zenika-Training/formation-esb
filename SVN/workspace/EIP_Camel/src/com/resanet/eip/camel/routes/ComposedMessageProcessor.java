package com.resanet.eip.camel.routes;

import static org.apache.camel.builder.ExpressionBuilder.beanExpression;
import static org.apache.camel.builder.PredicateBuilder.toPredicate;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Service;

@Service
public class ComposedMessageProcessor extends RouteBuilder {
	
	private static final String LOG = "log:" +  ComposedMessageProcessor.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {
	
		// Splitter
		from("direct:cmp")
			.split(body().regexTokenize("d.f"))
			.to("direct:cmpCBR");
		
		// CBR
		CMPBean cmpBean = new CMPBean();
		from("direct:cmpCBR")
			.choice() 
				.when(toPredicate(beanExpression("composedMessageProcessor")))
					.bean(cmpBean, "changeBody1")
				.otherwise()
					.bean(cmpBean, "changeBody2")
			.end()
			.to("direct:cmpAgg");

		// Aggregator
		AggregationStrategy  as = new ConcatAggregationStrategy();
		from("direct:cmpAgg")
			.aggregate(as).header("correlation.id")
			.to("direct:cmpOut");
		
		from("direct:cmpOut").to(LOG);
	}
	
	public boolean CBRBeanExpression(@Body String body){
		return body.startsWith("a");
	}
	
	/**
	 * Simple bean qui fait des concaténations.
	 *
	 */
	public class CMPBean{
		public String changeBody1(@Body String body){
			return body.concat(".changeBody1");
		}
		public String changeBody2(@Body String body){
			return body.concat(".changeBody2");
		}
	}
	
	/**
	 * Algorithme de concaténation des bodies uniquement.
	 *
	 */
	public class ConcatAggregationStrategy implements AggregationStrategy{
		
		public Exchange aggregate(Exchange oldEx, Exchange newEx) {
			
			StringBuilder sb = new StringBuilder();
			sb.append(oldEx.getIn().getBody(String.class));
			sb.append("++++++");
			sb.append(newEx.getIn().getBody(String.class));
			newEx.getIn().setBody(sb.toString(), String.class);
			
			return newEx;
		}
	}
}
