
package com.partenaires.resanet.hotel;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.partenaires.resanet.hotel package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RechercherHotelFault_QNAME = new QName("http://www.resanet.partenaires.com/hotel", "rechercherHotelFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.partenaires.resanet.hotel
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Hotel }
     * 
     */
    public Hotel createHotel() {
        return new Hotel();
    }

    /**
     * Create an instance of {@link RechercherHotelRequest }
     * 
     */
    public RechercherHotelRequest createRechercherHotelRequest() {
        return new RechercherHotelRequest();
    }

    /**
     * Create an instance of {@link RechercherHotelResponse }
     * 
     */
    public RechercherHotelResponse createRechercherHotelResponse() {
        return new RechercherHotelResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.resanet.partenaires.com/hotel", name = "rechercherHotelFault")
    public JAXBElement<String> createRechercherHotelFault(String value) {
        return new JAXBElement<String>(_RechercherHotelFault_QNAME, String.class, null, value);
    }

}
