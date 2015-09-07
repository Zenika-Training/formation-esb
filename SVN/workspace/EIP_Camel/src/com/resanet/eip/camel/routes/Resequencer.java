package com.resanet.eip.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.resequencer.DefaultExchangeComparator;

public class Resequencer extends RouteBuilder {
	
	private static final String LOG = "log:" +  Resequencer.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {
		
		// batch resequencer
		from("direct:resequencer")
			.resequencer(header("priority"))
			.batch().size(2).timeout(1000L)
			.to("direct:resequencerOut");

		
		// stream resequencer
		from("direct:resequencer2")
		.resequencer(header("priority"))
		.stream().timeout(1000L).comparator(new ReverseComparator())
		.to("direct:resequencerOut");

		from("direct:resequencerOut").to(LOG);
	}
	
	/**
	 * Comparateur de séquence inversée.
	 *
	 */
	public class ReverseComparator extends DefaultExchangeComparator{
		@Override // Retourne true si e1 est le successeur direct de e2.
		public boolean predecessor(Exchange e1, Exchange e2) {
			return getPriority(e1) == (getPriority(e2) +1);
		}
		
		@Override // Retourne true si e1 est le predecesseur direct de e2.
		public boolean successor(Exchange e1, Exchange e2) {
			return getPriority(e1) == getPriority(e2) - 1;
		}
	
		@Override // Compare les 2 valeurs.
		public int compare(Exchange e1, Exchange e2) {
			return getPriority(e2).compareTo(getPriority(e1));
		}
		
		private Long getPriority(Exchange e){
			return (Long) e.getIn().getHeader("priority"); }
	}
}
