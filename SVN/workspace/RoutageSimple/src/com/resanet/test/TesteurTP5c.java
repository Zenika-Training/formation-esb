package com.resanet.test;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import com.resanet.camel_su.routes.TP5c;

public class TesteurTP5c {

	@Test
	public void send() throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new TP5c());
		camelContext.start();

		ProducerTemplate producer = camelContext.createProducerTemplate();
		producer.sendBody("direct:mutlicast", "abcdefg");

		camelContext.stop();
	}
}
