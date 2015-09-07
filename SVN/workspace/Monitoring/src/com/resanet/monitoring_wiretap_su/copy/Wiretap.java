package com.resanet.monitoring_wiretap_su.copy;

import org.apache.camel.builder.RouteBuilder;

public class Wiretap extends RouteBuilder {

	private final static String NAMESPACE = "http://www.resanet.com/informations";
	private final static String SERVICE_IN = "jbi:service:" + NAMESPACE
			+ "/wiretap";
	private final static String SERVICE_OUT = "jbi:service:" + NAMESPACE
			+ "/detour";
	private final static String SERVICE_WIRETAP = "jbi:service:" + NAMESPACE
			+ "/wiretapSource";

	@Override
	public void configure() throws Exception {
		// source
		from(SERVICE_IN).wireTap(SERVICE_WIRETAP).to(SERVICE_OUT);
	}
}
