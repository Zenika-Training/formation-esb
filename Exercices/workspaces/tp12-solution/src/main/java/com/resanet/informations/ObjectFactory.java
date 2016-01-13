
package com.resanet.informations;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.resanet.informations package. 
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

    private final static QName _Detour_QNAME = new QName("http://www.resanet.com/informations", "detour");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.resanet.informations
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.resanet.com/informations", name = "detour")
    public JAXBElement<Boolean> createDetour(Boolean value) {
        return new JAXBElement<Boolean>(_Detour_QNAME, Boolean.class, null, value);
    }

}
