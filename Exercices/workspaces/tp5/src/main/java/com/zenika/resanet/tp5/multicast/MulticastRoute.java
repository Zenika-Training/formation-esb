package com.zenika.resanet.tp5.multicast;

import org.apache.camel.builder.RouteBuilder;

public class MulticastRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
        // En multicast
        from("direct:multicastRoute")
                .multicast()
                .to("bean:consumerA", "bean:consumerB", "bean:consumerC");

        // En multicast et parallel
        from("direct:multicastParallelRoute")
                .multicast().parallelProcessing()
                .to("bean:consumerA", "bean:consumerB", "bean:consumerC");

    }

}
