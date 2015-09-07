package com.resanet.camel_su.bean;

import java.util.Map;

public class Bean0 extends Bean {

	public void setHeader(Map<String, Object> headers) {
		headers.put("header.bean0", "abc");
	}
}
