package com.zenika.resanet.tp5.pipe;

import org.apache.camel.builder.RouteBuilder;

public class PipeRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
        // Sequentiel et dans l'ordre
        from("direct:pipeRoute")
                .to("bean:consumerA")
                .to("bean:consumerB")
                .to("bean:consumerC");

        // Ecriture équivalente
        from("direct:pipeRoute2")
                .to("bean:consumerA", "bean:consumerB", "bean:consumerC");

    }

}
