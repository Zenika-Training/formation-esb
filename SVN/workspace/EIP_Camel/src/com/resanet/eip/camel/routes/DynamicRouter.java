package com.resanet.eip.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.XPath;
import org.apache.camel.language.bean.BeanExpression;
import org.springframework.stereotype.Service;

@Service
public class DynamicRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("seda:dynamicRouter")
		.recipientList(new BeanExpression("dynamicRouter", "route"));
	}

	public String route(@XPath("/order/id") String orderID) {
		return "seda:out" + orderID;
	}
}
