package com.resanet.camel_su.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.logging.LogFactory;

public class Routes extends RouteBuilder {

	private static final String LOG = "log:" + Routes.class + "?showAll=true&multiline=true";

	@Override
	public void configure() {

		// pas de gestion d'erreur étant donné qu'on est dans un mode transaction
		errorHandler(noErrorHandler());
		
		from("jbi:endpoint:http://esb.resanet.com/CamelTrans/camelTrans")
		.to(LOG)
		.bean(new Routes(),"calculComplexeQuiDureLongtemps")
		.to(LOG)
		.to("jbi:endpoint:http://esb.resanet.com/jmsProviderTransOut/jmsProviderTransOut");

	}

	public void calculComplexeQuiDureLongtemps(Exchange ex) throws Exception {
		
		LogFactory.getLog(Routes.class).info("Je suis dans calculComplexeQuiDureLongtemps...");
		Thread.sleep(10000);

		
		StringBuilder sb = new StringBuilder();
		sb.append("<resultatComplexe xmlns=\"http://esb.resanet.com/pi\">");
		sb.append(Math.sqrt(Math.PI));
		sb.append("</resultatComplexe>");
		ex.getIn().setBody(sb.toString(), String.class);
		
		//throw new Exception("erreur !!!");
	}
}
