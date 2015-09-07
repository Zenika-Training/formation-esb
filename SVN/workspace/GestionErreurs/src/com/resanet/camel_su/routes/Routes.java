package com.resanet.camel_su.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;

public class Routes extends RouteBuilder {

	private static final String LOG = "log:" + Routes.class
			+ "?showAll=true&multiline=true";

	@Override
	public void configure() throws Exception {

		// erreur handler par défaut
		// ne s'applique pas avec try/catch
		//errorHandler(deadLetterChannel("amq:dlq"));
		
		// onException(Exception.class)
		//	.to("amq:dlq");
		

		/* -- Méthode avec try catch handler */
		
		from("amq:in")
		.to(LOG)
		.doTry()
			.to("validator:requete.xsd")
			.to("amq:out")
		.doCatch(ValidationException.class)
			.to(LOG)
			.to("amq:erreur")
		.doCatch(Exception.class)
			.to("amq:dlq")
		.end();
		.to(LOG);
		
		
		/* -- Méthode avec onException handler */
		// erreur handler pour la validation syntaxique
		
//		onException(ValidationException.class)
//		.to(LOG)
//		.to("amq:erreur");
//		

	}
}
