package com.resanet.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.resanet.common.Hotel;
import com.resanet.common.ObjectFactory;
import com.resanet.common.RechercherHotelFault;


@WebService(targetNamespace = "http://www.resanet.partenaires.com/hotel", name = "partenairePortType")
@XmlSeeAlso( { ObjectFactory.class })
public interface HotelPortType {

	@WebResult(name = "hotel", targetNamespace = "http://www.resanet.partenaires.com/hotel")
	@RequestWrapper(localName = "trouverHotel", targetNamespace = "http://www.resanet.partenaires.com/hotel", className = "com.resanet.ws.hotel.RechercherHotel")
	@ResponseWrapper(localName = "trouverHotelResponse", targetNamespace = "http://www.resanet.partenaires.com/hotel", className = "com.resanet.ws.hotel.RechercherHotelResponse")
	@WebMethod
	public Hotel trouverHotel(
			@WebParam(name = "reference", targetNamespace = "http://www.resanet.partenaires.com/hotel") java.lang.String reference)
			throws RechercherHotelFault;
}
