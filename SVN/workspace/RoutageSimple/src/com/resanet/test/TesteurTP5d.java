package com.resanet.test;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import com.resanet.camel_su.routes.TP5d;

public class TesteurTP5d {

	@Test
	public void send() throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new TP5d());
		camelContext.start();

		ProducerTemplate producer = camelContext.createProducerTemplate();
		producer.sendBody("direct:messageTranslator", "Hello ");

		camelContext.stop();
	}
}
