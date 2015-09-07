package com.resanet.monitoring_detour_su;

import org.apache.camel.builder.RouteBuilder;

public class Detour extends RouteBuilder {

	private final static String NAMESPACE = "http://www.resanet.com/informations";
	private final static String SERVICE_IN = "jbi:service:" + NAMESPACE
			+ "/detour";
	private final static String SERVICE_OUT = "jbi:service:" + NAMESPACE
			+ "/traitement";
	private final static String SERVICE_DETOUR_CONTROLE = "jbi:service:"
			+ NAMESPACE + "/detourControle";
	private final static String SERVICE_DETOUR = "jbi:service:" + NAMESPACE
			+ "/validation";

	@Override
	public void configure() throws Exception {
		// controle du detour
		from(SERVICE_DETOUR_CONTROLE).beanRef("detourControle","changeDetour");

		// detour
		from(SERVICE_IN).choice().when().method("detourControle", "isDetour")
				.to(SERVICE_DETOUR).otherwise().to(SERVICE_OUT).end();
	}
}
