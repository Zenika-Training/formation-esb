package com.zenika.resanet.tp6.beans;

import java.util.Map;

public class Bean2 extends Bean {
	public void setHeader(Map<String, Object> headers) {
		headers.put("header.bean2", "ghi");
	}
}
