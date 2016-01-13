package com.resanet.esb.tp10.trycatch;

import com.resanet.esb.tp10.AbstractRoute;
import org.apache.camel.ValidationException;

public class Routes extends AbstractRoute {

	public Routes() {
		super("C");
	}
	
	@Override
	public void configure() throws Exception {
		
		errorHandler(noErrorHandler());
		
		// Methode avec try catch handler
		from(IN_QUEUE)
			.to(LOG)
			.doTry()
				.to("validator:requete.xsd")
				.bean(new Routes(), "error")
				.to(OUT_QUEUE_VALID)
			.doCatch(ValidationException.class)
				.to(LOG)
				.to(OUT_QUEUE_NOT_VALID)
			.doCatch(Exception.class)
				.to(DEADLETTER_QUEUE)
				.to(LOG);
		
	}

}
