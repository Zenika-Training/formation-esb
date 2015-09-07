package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class LoadBalancer extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// round robin
		from("seda:lbrRoundRobin")
			.loadBalance().roundRobin()
			.to("seda:lbrrr1","seda:lbrrr2","seda:lbrrr3");
		
		// random
		from("seda:lbrRandom")
			.loadBalance().random()
			.to("seda:lbrr1","seda:lbrr2","seda:lbrr3");
		
		// sticky
		from("seda:lbrSticky")
			.loadBalance().sticky(header("JMSXGroupID"))
			.to("seda:lbrs1","seda:lbrs2","seda:lbrs3");
		
		
		from("seda:lbrrr1").to("log:"+ LoadBalancer.class + "lbrrr1?showAll=true&multiline=true");
		from("seda:lbrrr2").to("log:"+ LoadBalancer.class + "lbrrr2?showAll=true&multiline=true");
		from("seda:lbrrr3").to("log:"+ LoadBalancer.class + "lbrrr3?showAll=true&multiline=true");
		
		from("seda:lbrr1").to("log:"+ LoadBalancer.class + "lbrr1?showAll=true&multiline=true");
		from("seda:lbrr2").to("log:"+ LoadBalancer.class + "lbrr2?showAll=true&multiline=true");
		from("seda:lbrr3").to("log:"+ LoadBalancer.class + "lbrr3?showAll=true&multiline=true");
		
		from("seda:lbrs1").to("log:"+ LoadBalancer.class + "lbrs1?showAll=true&multiline=true");
		from("seda:lbrs2").to("log:"+ LoadBalancer.class + "lbrs2?showAll=true&multiline=true");
		from("seda:lbrs3").to("log:"+ LoadBalancer.class + "lbrs3?showAll=true&multiline=true");
	}
}
