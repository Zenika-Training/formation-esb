package com.resanet.esb.tp10;

import org.apache.camel.Body;
import org.apache.camel.builder.RouteBuilder;

public abstract class AbstractRoute extends RouteBuilder {

	protected final String LOG;

	private static final String PREFIX = "amq:tp10_";
	
	protected final String IN_QUEUE;
	protected final String OUT_QUEUE_VALID;
	protected final String OUT_QUEUE_NOT_VALID;
	protected final String DEADLETTER_QUEUE;
	
	public AbstractRoute(String id) {
		LOG = "log:" + id + "?showAll=true&multiline=true";
		IN_QUEUE = PREFIX + id + "_in";
		OUT_QUEUE_VALID = PREFIX + id + "_out_valid";
		OUT_QUEUE_NOT_VALID = PREFIX + id + "_out_not_valid";
		DEADLETTER_QUEUE = PREFIX + id + "_dlq";
	}
	
	/**
	 * Declenche une erreur si la chaine XML contient le mot ERREUR
	 * @param xml
	 * @throws Exception
	 */
	public void error(@Body String xml) throws Exception {
		if (xml.contains("ERREUR")) {
			throw new Exception();
		}
	}
	
}
