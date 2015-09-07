package com.resanet.monitoring_detour_su;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.resanet.common.ObjectFactory;

public class DetourControle {

	boolean detour = true;

	public boolean isDetour() {
		return detour;
	}

	public void setDetour(boolean detour) {
		this.detour = detour;
	}

	@SuppressWarnings("unchecked")
	public void changeDetour(String body) {
		try {
			JAXBContext contextReservation = JAXBContext
					.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = contextReservation.createUnmarshaller();
			ByteArrayInputStream input = new ByteArrayInputStream(body.getBytes()); 
			JAXBElement<Boolean> element = (JAXBElement<Boolean>)unmarshaller.unmarshal(input);
			
			setDetour(element.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
