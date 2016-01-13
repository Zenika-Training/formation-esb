package com.zenika.resanet.tp5.beans;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsumerC {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerC.class);

    public String sayHello(@Body String message) {
        LOGGER.info("C");
        message += " C";
        LOGGER.debug(message);
        return message;
    }
}
