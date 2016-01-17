package com.resanet.esb.tp12.detour;

import org.apache.camel.builder.RouteBuilder;

public class DetourRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// controle du detour
		from("jetty:http://localhost:8085/detour").bean("detourControle","changeDetour");

        // aussi en WS
        from("cxf:bean:detourEndpoint")
                .bean("detourControle","changeDetour")
        .transform(constant("OK"));

		// detour
		from("jms:queue:in_detour")
		.choice()
			.when().method("detourControle", "isDetour")
				.to("jms:queue:out_detour1")
			.otherwise()
				.to("jms:queue:out_detour2");
	}
}
