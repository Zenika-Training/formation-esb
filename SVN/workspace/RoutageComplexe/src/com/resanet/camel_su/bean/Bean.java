package com.resanet.camel_su.bean;

import java.util.Map;

import org.apache.camel.Exchange;

public abstract class Bean implements Worker {
	public String work(String body, Exchange exchange) {
		setHeader(exchange.getIn().getHeaders());
		return body.replace("message", "bean");
	}

	public abstract void setHeader(Map<String, Object> headers);
}
