package com.zenika.resanet.tp6.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/cbr-context-test.xml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:(split|outNotification)")
@DisableJmx
public class ContentBasedRouterTest {

    private static final String REQUEST = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<requete xmlns=\"http://esb.resanet.com\">\n" +
            "\t<message1>\n" +
            "\t\t<camel>123</camel>\n" +
            "\t</message1>\n" +
            "\t<message2>\n" +
            "\t\t<smx>456</smx>\n" +
            "\t</message2>\n" +
            "\t<message3>\n" +
            "\t\t<amq>789</amq>\n" +
            "\t</message3>\n" +
            "</requete>";

    private static final String NOTIFICATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<notification xmlns=\"http://esb.resanet.com\">\n" +
            "\t<event>OK</event>\n" +
            "</notification>";

    @Produce(uri = "direct:cbr")
    private ProducerTemplate cbr;

    @EndpointInject(uri = "mock:direct:split")
    private MockEndpoint split;

    @EndpointInject(uri = "mock:direct:outNotification")
    private MockEndpoint outNotification;

    @Test
    public void should_route_to_splitter() throws InterruptedException {

        split.expectedBodiesReceived(REQUEST);

        cbr.sendBody(REQUEST);

        split.assertIsSatisfied();
    }

    @Test
    public void should_route_to_outNotification() throws InterruptedException {

        outNotification.expectedBodiesReceived(NOTIFICATION);

        cbr.sendBody(NOTIFICATION);

        outNotification.assertIsSatisfied();
    }

}