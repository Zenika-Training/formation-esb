package com.zenika.resanet.tp6.routes;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/aggregator-context-test.xml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("jms:*")
@DisableJmx
public class AggregatorTest {

    private static final String BEAN1 = "<bean1 xmlns=\"http://esb.resanet.com\">" +
            "<camel>123</camel>" +
            "</bean1>";

    private static final String BEAN2 = "<bean2 xmlns=\"http://esb.resanet.com\">" +
            "<smx>456</smx>" +
            "</bean2>";

    private static final String BEAN3 = "<bean3 xmlns=\"http://esb.resanet.com\">" +
            "<amq>789</amq>" +
            "</bean3>";

    private static final String RESPONSE = "<reponse xmlns=\"http://esb.resanet.com\">\n" +
            "<bean1 xmlns=\"http://esb.resanet.com\">" +
            "<camel>123</camel>" +
            "</bean1>" +
            "<bean2 xmlns=\"http://esb.resanet.com\">" +
            "<smx>456</smx>" +
            "</bean2>" +
            "<bean3 xmlns=\"http://esb.resanet.com\">" +
            "<amq>789</amq>" +
            "</bean3>" +
            "\n</reponse>";

    @Produce(uri = "direct:aggregator")
    private ProducerTemplate aggregator;

    @EndpointInject(uri = "mock:jms:queue:paire")
    private MockEndpoint paire;

    @EndpointInject(uri = "mock:jms:queue:impaire")
    private MockEndpoint impaire;

    @Autowired
    private CamelContext camelContext;

    @Test
    public void should_route_to_paire() throws InterruptedException {

        Exchange exchange1 = new DefaultExchange(camelContext);
        exchange1.getIn().setHeader("JMSCorrelationID", 1);
        exchange1.setProperty(Exchange.SPLIT_SIZE, 3);
        exchange1.getIn().setBody(BEAN1);

        Exchange exchange2 = exchange1.copy();
        exchange2.getIn().setBody(BEAN2);

        Exchange exchange3 = exchange1.copy();
        exchange3.getIn().setBody(BEAN3);

        paire.expectedBodiesReceived(RESPONSE);

        aggregator.send(exchange1);
        aggregator.send(exchange2);
        aggregator.send(exchange3);

        paire.assertIsSatisfied();

        impaire.expectedBodiesReceived(RESPONSE);

        aggregator.send(exchange1);
        aggregator.send(exchange2);
        aggregator.send(exchange3);

        impaire.assertIsSatisfied();
    }

}