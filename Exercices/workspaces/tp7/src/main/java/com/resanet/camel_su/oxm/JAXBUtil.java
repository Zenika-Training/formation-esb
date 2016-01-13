package com.resanet.camel_su.oxm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Utilitaire de mapping JAXB.
 * 
 */
public class JAXBUtil {

	/**
	 * Transforme un XML en Object.
	 * 
	 * @param source le XML
	 * @param objectFactoryClasses les registres JAXB
	 */
	public static Object unmarshall(Source source, Class<?>... objectFactoryClasses) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(objectFactoryClasses);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return unmarshaller.unmarshal(source);
	}

	/**
	 * Transforme un Object en XML.
	 * 
	 * @param object l'objet annote
	 * @param objectFactoryClasses les registres JAXB
	 */
	public static Source marshall(Object object, Class<?>... objectFactoryClasses) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(objectFactoryClasses);
		Marshaller marshaller = jaxbContext.createMarshaller();
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		marshaller.marshal(object, result);
		return new StreamSource(new StringReader(writer.toString()));
	}
	
	/**
	 * Transforme un Object en XML.
	 * 
	 * @param object l'objet annote
	 * @param objectFactoryClasses les registres JAXB
	 */
	public static String marshalltoString(Object object, Class<?>... objectFactoryClasses) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(objectFactoryClasses);
		Marshaller marshaller = jaxbContext.createMarshaller();
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		marshaller.marshal(object, result);
		return writer.toString();
	}


}
