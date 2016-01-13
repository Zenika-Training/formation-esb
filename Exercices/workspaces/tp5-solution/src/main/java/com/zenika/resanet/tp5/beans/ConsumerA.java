package com.zenika.resanet.tp5.beans;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsumerA {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerA.class);

    public String sayHello(@Body String message) {
        LOGGER.info("A");
        message += " A";
        LOGGER.debug(message);
        return message;
    }
}
