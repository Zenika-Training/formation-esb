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

public class TesteurRoutes {

	private CamelContext camelContext;

	private ProducerTemplate producer;

	@Before
	public void init() throws Exception {
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

		 contentBasedRouter();
		 messageFilter();
		 dynamicRouter();
		 recipientListStatic();
		 recipientListDynamique();
		 splitter();
		 aggregator();
		 resequencer();
		 composedMessageProcessor();
		 scatterGather();
		 routingSlip();
		 throttler();
		 delayer();
		 loadbalancer();
		 multicast();
		 loop();

		Thread.sleep(15000);
	}

	public void contentBasedRouter() {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("orderType", "gadget");
		producer.sendBodyAndHeaders("seda:cbr", "contentBasedRouter", headers);
	}

	public void messageFilter() {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("orderType", "gadget");
		producer.sendBodyAndHeaders("seda:filter", "filter", headers);
	}

	public void dynamicRouter() {
		producer.sendBody("seda:dynamicRouter", "<order><id>1</id></order>");
	}

	public void recipientListStatic() {

		// multicast séquentiel
		producer.sendBody("seda:rlsin", "sequentiel");

		// mutlicast parallèle
		producer.sendBody("seda:rlsinp", "parallele");
	}

	public void recipientListDynamique() {

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("to", "seda:rldout1#seda:rldout2");
		producer.sendBodyAndHeaders("seda:rldin", "body", headers);
	}

	public void splitter() {

		// xpath
		producer.sendBody("seda:splitterXPath", "<order xmlns=\"http://esb.resanet.com\">" + "<commande>"
				+ "<id>123</id>" + "</commande>" + "</order>");

		// regex
		producer.sendBody("seda:splitterRegex", "abcdefghi");
	}

	public void aggregator() throws InterruptedException {

		Map<String, Object> headers = new HashMap<String, Object>();

		// via headers correlation_id
		headers = new HashMap<String, Object>();
		headers.put("correlation.id", "683Y74");
		producer.sendBodyAndHeaders("seda:aggregator", "body1", headers);
		producer.sendBodyAndHeaders("seda:aggregator", "body2", headers);
		producer.sendBodyAndHeaders("seda:aggregator", "body3", headers);
		Thread.sleep(3000);
		producer.sendBodyAndHeaders("seda:aggregator", "body4", headers);

		// via Exchange.AGGREGATED_COUNT
		producer = camelContext.createProducerTemplate();
		headers = new HashMap<String, Object>();
		headers.put("correlation.id", "99999XX");
		producer.sendBodyAndHeaders("seda:aggregator2", "body5", headers);
		producer.sendBodyAndHeaders("seda:aggregator2", "body6", headers);
		producer.sendBodyAndHeaders("seda:aggregator2", "body7", headers);
		producer.sendBodyAndHeaders("seda:aggregator2", "body8", headers);

		producer = camelContext.createProducerTemplate();
		producer.sendBody("seda:aggregator3", "<body><a/></body>");
		producer.sendBody("seda:aggregator3", "<body><b/></body>");
		producer.sendBody("seda:aggregator3", "<body><c/></body>");
		producer.sendBody("seda:aggregator3", "<body><d/></body>");
	}

	public void resequencer() throws InterruptedException {

		Map<String, Object> headers = new HashMap<String, Object>();

		// batch resequencer
		headers.put("priority", 5);
		producer.sendBodyAndHeaders("direct:resequencer", "abc", headers);
		headers.put("priority", 3);
		producer.sendBodyAndHeaders("direct:resequencer", "def", headers);
		headers.put("priority", 4);
		producer.sendBodyAndHeaders("direct:resequencer", "ghi", headers);
		headers.put("priority", 1);
		producer.sendBodyAndHeaders("direct:resequencer", "jkl", headers);

		// stream resequencer
		producer = camelContext.createProducerTemplate();
		headers = new HashMap<String, Object>();

		headers.put("priority", 5L);
		producer.sendBodyAndHeaders("direct:resequencer2", "hello world 5", headers);
		headers.put("priority", 3L);
		producer.sendBodyAndHeaders("direct:resequencer2", "hello world 3", headers);
		Thread.sleep(3000);

		headers.put("priority", 4L);
		producer.sendBodyAndHeaders("direct:resequencer2", "hello world 4", headers);
		headers.put("priority", 1L);
		producer.sendBodyAndHeaders("direct:resequencer2", "hello world 1", headers);
		headers.put("priority", 2L);
		producer.sendBodyAndHeaders("direct:resequencer2", "hello world 2", headers);
		headers.put("priority", 3L);
		producer.sendBodyAndHeaders("direct:resequencer2", "hello world 3", headers);
	}

	public void composedMessageProcessor() throws InterruptedException {

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("correlation.id", "123");
		producer.sendBodyAndHeaders("direct:cmp", "abcdefghi", headers);

	}

	public void scatterGather() throws InterruptedException {

		Map<String, Object> headers = new HashMap<String, Object>();

		headers.put("catalogue", "bean:scatterGatherBean?method=changeBody1,"
				+ "bean:scatterGatherBean?method=changeBody2," + "bean:scatterGatherBean?method=changeBody3");
		headers.put("correlation.id", "XX123");

		producer.sendBodyAndHeaders("direct:scattergather", "abcdefghi", headers);
	}

	public void routingSlip() throws InterruptedException {

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("route", "direct:rs1,direct:rs2,direct:rs3");
		producer.sendBodyAndHeaders("seda:routingslip", "a", headers);
	}

	public void throttler() {
		for (int i = 1; i <= 30; i++) {
			producer.sendBody("seda:throttler", "[" + i + "]");
		}
	}

	public void delayer() throws InterruptedException {

		for (int i = 1; i <= 5; i++) {
			producer.sendBody("seda:delayer", "[" + i + "]");
		}
		Thread.sleep(5000);

		producer = camelContext.createProducerTemplate();
		Map<String, Object> headers = new HashMap<String, Object>();
		for (int i = 1; i <= 5; i++) {
			headers.put("delai", System.currentTimeMillis() + i * 1000);
			producer.sendBodyAndHeaders("seda:delayer2", "delayer", headers);
		}
	}

	public void loadbalancer() throws InterruptedException {

		for (int i = 1; i <= 10; i++) {
			producer.sendBody("seda:lbrRoundRobin", "hello world RoundRobin!");
		}

		Thread.sleep(1000);
		for (int i = 1; i <= 10; i++) {
			producer.sendBody("seda:lbrRandom", "hello world Random!");
		}

		Thread.sleep(1000);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("JMSXGroupID", "abc");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "abc");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "abc");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "def");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "abc");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "def");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "ghi");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "ghi");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "ghi");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "ghi");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);
		headers.put("JMSXGroupID", "ghi");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);

		headers.put("JMSXGroupID", "zzz");
		producer.sendBodyAndHeaders("seda:lbrSticky", "hello world Sticky!", headers);

	}

	public void multicast() {

		// séquentiel
		producer.sendBody("seda:multicasts", "hello world en séquentiel !");

		// parallèle
		producer.sendBody("seda:multicastp", "hello world en parallèle !");

		producer.sendBody("direct:multicastAggStg", "hello world en avec AggStg !");

	}

	public void loop() {
		producer.sendBody("seda:loop", "<body><id/><nom/><nbOrders>5</nbOrders></body>");
	}
}
