package com.zenika.resanet.tp5.cbr;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/camel-context.xml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@DisableJmx
public class ContentBasedRouteTest {

    private static final String HELLO = "Hello";

    @Produce(uri = "direct:cbr")
    protected ProducerTemplate cbrRoute;

    @Test
    public void shouldSendAReservationToEndpointCBR() {
        cbrRoute.sendBodyAndHeader(HELLO, "MESSAGE_TYPE", "reservation");
    }

    @Test
    public void shouldSendAPromotionToEndpointCBR() {
        cbrRoute.sendBodyAndHeader(HELLO, "MESSAGE_TYPE", "promotion");
    }

    @Test
    public void shouldSendSomethingToEndpointCBR() {
        cbrRoute.sendBodyAndHeader(HELLO, "MESSAGE_TYPE", "???");
    }

}
