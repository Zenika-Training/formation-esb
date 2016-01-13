package com.resanet.esb.tp10.deadletterchannel;

import org.apache.camel.ValidationException;

import com.resanet.esb.tp10.AbstractRoute;

public class Routes extends AbstractRoute {

	public Routes() {
		super("A");
	}

	@Override
	public void configure() throws Exception {

		errorHandler(deadLetterChannel(DEADLETTER_QUEUE));

		onException(ValidationException.class)
			.to(LOG)
			.to(OUT_QUEUE_NOT_VALID);

		from(IN_QUEUE)
			.to("validator:requete.xsd")
			.bean(new Routes(), "error")
			.to(OUT_QUEUE_VALID);

    }
	
}
