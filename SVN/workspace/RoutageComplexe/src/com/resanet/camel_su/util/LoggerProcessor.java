package com.resanet.camel_su.util;

import java.util.Map.Entry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerProcessor implements Processor {

	/** Le Logger. */
	private Log log;

	/**
	 * Construit le LoggerProcessor.
	 * 
	 * @param log le logger
	 */
	public LoggerProcessor(Class<?> clazz) {
		log = LogFactory.getLog(clazz);
	}

	public void process(Exchange exchange) throws Exception {
		log.info("");
		log.info("------------------------------------------------");
		log.info("Exchange : " + exchange);
		log.info("Pattern : " + exchange.getPattern());
		log.info("In : " + exchange.getIn().getBody());
		log.info("In Headers");
		for (Entry<String, Object> entry : exchange.getIn().getHeaders().entrySet()) {
			log.info("\tkey : " + entry.getKey());
			log.info("\tvalue : " + entry.getValue());
		}
		log.info("Out : " + exchange.getOut().getBody());
		log.info("Out Headers");
		for (Entry<String, Object> entry : exchange.getIn().getHeaders().entrySet()) {
			log.info("\tkey : " + entry.getKey());
			log.info("\tvalue : " + entry.getValue());
		}
		log.info("------------------------------------------------");
		log.info("");
	}
}
