package com.resanet.eip.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TesteurTransformations {

	private CamelContext camelContext;

	private ProducerTemplate producer;

	@Before
	public void initContext() throws Exception {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/camel-context.xml");
		camelContext = (CamelContext) beanFactory.getBean("camel");
		producer = camelContext.createProducerTemplate();
	}

	@After
	public void shutdown() throws Exception {
		producer.stop();
		camelContext.stop();
	}

	@Test
	public void run() throws InterruptedException {

		messageTranslator();
		contentEnricher();
		contentFilter();
		claimCheck();
		normalizer();

		Thread.sleep(2000);
	}

	public void messageTranslator() {
		producer.sendBody("direct:msgTransProc", "Hello");
		producer.sendBody("direct:msgTransTransf", "Hello");
	}

	public void contentEnricher() {
		producer.sendBody("direct:contentEnricher", "Nous sommes le ");
	}

	public void contentFilter() {
		String xml = "<requeteComplete xmlns=\"http://esb.resanet.com\">" + "<data>1234</data>" + "<data2>5678</data2>"
				+ "</requeteComplete>";
		producer.sendBody("direct:contentFilter", xml);
	}

	public void claimCheck() {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("id", "1234XX");
		producer.sendBodyAndHeaders("direct:claimCheck", "Rendez moi mes bagages !", headers);
	}

	public void normalizer() {

		String xmlA = "<formatA xmlns=\"http://esb.resanet.com/A\"><unebalise><uneAutreBalise><id>12345</id></uneAutreBalise></unebalise></formatA>";
		String xmlB = "<formatB xmlns=\"http://esb.resanet.com/B\"><id>67890</id></formatB>";

		producer.sendBody("direct:normalizer", xmlA);
		producer.sendBody("direct:normalizer", xmlB);
	}
}
