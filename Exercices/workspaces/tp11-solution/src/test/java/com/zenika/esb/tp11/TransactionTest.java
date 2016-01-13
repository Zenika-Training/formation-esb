package com.zenika.esb.tp11;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.Policy;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionTest extends CamelTestSupport {

    private static ApplicationContext springContext;
    private static SpringCamelContext camelContext;
    private static SpringCamelContext camelTemplate;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // setup application context and camel context
        springContext = new ClassPathXmlApplicationContext("/context.xml");
        camelContext = getCamelContext(springContext);

        // configure routes and add to camel context

        RouteBuilder route = new SpringRouteBuilder() {

           @Override
            public void configure() throws Exception {
               Policy required = new SpringTransactionPolicy(lookup("PROPAGATION_REQUIRED",TransactionTemplate.class));


               errorHandler(transactionErrorHandler().disableRedelivery());
               onException(Exception.class)
                       .handled(true).maximumRedeliveries(0)
                       .to("log:error processing message");


               from("activemq:queue.a")
                       .transacted().policy(required)
                        // no error handler needed here
                                // installs transaction interceptor

                                // throws exception causing a rollback
                        .rollback()
                        .process(new Processor() {

                            @Override
                            public void process(Exchange exchange) throws Exception {
                                // should be printed n times due to redeliveries
                                System.out.println("exchange = " + exchange);
                                // force rollback
                                throw new Exception("test");
                            }

                        });

            }

        };

        camelContext.addRoutes(route);
    }

    @Test
    public void testRollback() throws InterruptedException {
        template.sendBody("activemq:queue.a", "blah");
        // wait for 20 seconds to see redeliveries
        // (exchange string rep. written to stdout)
        Thread.sleep(20000L);
    }

    private static SpringCamelContext getCamelContext(ApplicationContext
                                                              springContext) {
        return (SpringCamelContext)springContext.getBean("camel");
    }

}
