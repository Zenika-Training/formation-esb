package com.resanet.camel_su;

import org.apache.camel.builder.RouteBuilder;

public class PipelineReservationAirAlpha extends RouteBuilder {

	private static final String NS_RESANET = "http://esb.resanet.com/";

	private static final String NS_AIRALPHA = "http://airalpha.com/services/1.0";

	private static final String JBI_ENDPOINT = "jbi:endpoint:";

	private static final String SERVICE_IN = "/resaVersAirAlpha/jmsToWs";

	private static final String SERVICE_TO_1 = "/airAlphaService/airAlphaPort?mep=in-out";

	private static final String SERVICE_TO_2 = "/resaJMSProviderTP5/versReservation";

	/**
	 * Configuration du pipeline.
	 */
	@Override
	public void configure() throws Exception {
		from(JBI_ENDPOINT + NS_RESANET + SERVICE_IN)
		.pipeline(JBI_ENDPOINT + NS_AIRALPHA + SERVICE_TO_1
				, JBI_ENDPOINT + NS_RESANET + SERVICE_TO_2);
	}
}
