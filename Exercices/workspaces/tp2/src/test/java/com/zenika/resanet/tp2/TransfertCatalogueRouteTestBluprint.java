package com.zenika.resanet.tp2;

import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.*;
import org.springframework.test.context.ContextConfiguration;


@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/aggregator-context-test.xml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("jms:*")
@DisableJmx
public class TransfertCatalogueRouteTestBluprint {


}
