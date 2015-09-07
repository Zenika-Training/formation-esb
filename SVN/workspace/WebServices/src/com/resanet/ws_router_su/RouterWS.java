package com.resanet.ws_router_su;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;

public class RouterWS extends RouteBuilder {

	private final static String NAMESPACE = "http://www.resanet.partenaires.com/hotel";
	private final static String SERVICE_IN = "jbi:service:" + NAMESPACE
			+ "/camelReceiver";
	private final static String SERVICE_OUT = "jbi:service:" + NAMESPACE
			+ "/hotelService";
	private final static String SERVICE_WIRETAP = "jbi:service:" + NAMESPACE
			+ "/WSLog?mep=in-only";
	private final static String SERVICE_OUT_2 = "jbi:service:"
			+ NAMESPACE
			+ "/xslt?operation={http://www.resanet.partenaires.com/hotel}trouverHotel";

	@Override
	public void configure() throws Exception {
		
		Namespaces ns = new Namespaces("ns", NAMESPACE);
		from(SERVICE_IN)
			.choice()
				.when().xpath("starts-with(//ns:reference,'REF')", ns)
					.to(SERVICE_OUT)
				
				.otherwise()
					.multicast().to(SERVICE_WIRETAP, SERVICE_OUT_2);
	}
}
