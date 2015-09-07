package com.resanet.eip.camel.routes;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScatterGatherBean {

	@Produce(uri = "direct:sgAgg")
	private ProducerTemplate sgAgg;

	public void changeBody1(@Body String body, Exchange exchange) {
		exchange.getIn().setBody(body.concat(".changeBody1"));
		sgAgg.send(exchange);
	}

	public void changeBody2(@Body String body, Exchange exchange) {
		exchange.getIn().setBody(body.concat(".changeBody2"));
		sgAgg.send(exchange);
	}

	public void changeBody3(@Body String body, Exchange exchange) {
		exchange.getIn().setBody(body.concat(".changeBody3"));
		sgAgg.send(exchange);
	}

}
