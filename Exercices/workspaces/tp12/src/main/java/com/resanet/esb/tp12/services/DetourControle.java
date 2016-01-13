package com.resanet.esb.tp12.services;


import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetourControle {

	private static final Logger LOG = LoggerFactory.getLogger(DetourControle.class);

    boolean detour = true;

	public boolean isDetour() {
		return detour;
	}

	public void setDetour(boolean detour) {
		this.detour = detour;
	}

	public void changeDetour(@Body Boolean detour) {
		LOG.info("changing detour to " + detour);
        this.detour = detour;
	}
}
