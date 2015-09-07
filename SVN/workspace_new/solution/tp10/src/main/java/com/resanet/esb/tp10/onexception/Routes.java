package com.resanet.esb.tp10.onexception;

import com.resanet.esb.tp10.AbstractRoute;
import org.apache.camel.ValidationException;

public class Routes extends AbstractRoute {

	public Routes() {
		super("B");
	}
	
	@Override
	public void configure() throws Exception {

		// Utiliser un noErrorHandler desactive les clauses onException
		// errorHandler(noErrorHandler());

		onException(ValidationException.class)
			.to(LOG)
			.to(OUT_QUEUE_NOT_VALID);

		onException(Exception.class)
			.to(DEADLETTER_QUEUE);

		from(IN_QUEUE)
			.to("validator:requete.xsd")
			.bean(new Routes(), "error")
			.to(OUT_QUEUE_VALID);

	}

}
