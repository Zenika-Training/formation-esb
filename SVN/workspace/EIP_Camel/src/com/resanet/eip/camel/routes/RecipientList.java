package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class RecipientList extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		// routage statique
		from("seda:rlsin")
		.multicast()
		.to("seda:rlsout1","seda:rlsout2");
		
		// routage statique parallèle
		from("seda:rlsinp")
		.multicast().parallelProcessing()
		.to("seda:rlsout1","seda:rlsout2");
		
		// routage dynamique séquentiel
		from("seda:rldin")
		.recipientList(header("to").tokenize("#"));
	}
}
