package com.zenika.resanet.tp6.routes;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/splitter-context-test.xml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:aggregator")
@DisableJmx
public class SplitterTest {

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

    @Produce(uri = "direct:split")
    private ProducerTemplate split;

    @EndpointInject(uri = "mock:direct:aggregator")
    private MockEndpoint aggregator;

    @Test
    public void should_route_to_aggregator() throws InterruptedException {

        aggregator.expectedMessageCount(3);

        split.sendBody(REQUEST);

        aggregator.assertIsSatisfied();
    }

}