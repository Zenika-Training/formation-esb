package com.zenika.resanet.tp6.beans;

import org.apache.camel.Exchange;

import java.util.Map;

public abstract class Bean implements Worker {
	public String work(String body, Exchange exchange) {
		setHeader(exchange.getIn().getHeaders());
		return body.replace("message", "bean");
	}

	public abstract void setHeader(Map<String, Object> headers);
}
