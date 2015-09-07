package com.resanet.ws_se_su;

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

@WebService(targetNamespace = "http://www.resanet.partenaires.com/hotel", name = "hotelPortType")
@XmlSeeAlso( { ObjectFactory.class })
public interface HotelPortType {

	@WebResult(name = "hotel", targetNamespace = "http://www.resanet.partenaires.com/hotel")
	@RequestWrapper(localName = "rechercherHotel", targetNamespace = "http://www.resanet.partenaires.com/hotel", className = "com.partenaires.resanet.hotel.RechercherHotel")
	@ResponseWrapper(localName = "rechercherHotelResponse", targetNamespace = "http://www.resanet.partenaires.com/hotel", className = "com.partenaires.resanet.hotel.RechercherHotelResponse")
	@WebMethod
	public Hotel rechercherHotel(
			@WebParam(name = "reference", targetNamespace = "http://www.resanet.partenaires.com/hotel") java.lang.String reference,
			@WebParam(mode = WebParam.Mode.OUT, name = "origine", targetNamespace = "http://www.resanet.partenaires.com/hotel", header = true) javax.xml.ws.Holder<java.lang.String> parameters)
			throws RechercherHotelFault;
}
