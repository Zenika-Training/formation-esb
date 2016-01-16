package com.zenika.resanet.tp2;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.zenika.resanet.configuration.Tp2CamelConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {Tp2CamelConfiguration.class},
        loader = CamelSpringDelegatingTestContextLoader.class
    )
@MockEndpoints
public class TransfertCatalogueRouteJavaConfigTest {

    @EndpointInject(uri = "mock:direct:to")
    private MockEndpoint toEndpoint;

    @Produce(uri = "direct:from")
    private ProducerTemplate testProducer;
    
    @Test
    public void testRoute() throws InterruptedException {
    	toEndpoint.expectedMessageCount(1);
 
        testProducer.sendBody("<name>test</name>");
 
        toEndpoint.assertIsSatisfied();
    }
}
