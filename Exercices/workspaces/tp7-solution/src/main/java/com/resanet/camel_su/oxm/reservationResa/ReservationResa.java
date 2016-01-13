//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.15 at 12:27:40 PM CET 
//


package com.resanet.camel_su.oxm.reservationResa;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attributsReservation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="idReservation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="dateReservation" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="produits" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codeProduit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="tarif" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                   &lt;element name="quantite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attributsReservation",
    "produits"
})
@XmlRootElement(name = "reservation")
public class ReservationResa {

    @XmlElement(required = true)
    protected ReservationResa.AttributsReservation attributsReservation;
    @XmlElement(required = true)
    protected List<ReservationResa.Produits> produits;

    /**
     * Gets the value of the attributsReservation property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationResa.AttributsReservation }
     *     
     */
    public ReservationResa.AttributsReservation getAttributsReservation() {
        return attributsReservation;
    }

    /**
     * Sets the value of the attributsReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationResa.AttributsReservation }
     *     
     */
    public void setAttributsReservation(ReservationResa.AttributsReservation value) {
        this.attributsReservation = value;
    }

    /**
     * Gets the value of the produits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the produits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProduits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReservationResa.Produits }
     * 
     * 
     */
    public List<ReservationResa.Produits> getProduits() {
        if (produits == null) {
            produits = new ArrayList<ReservationResa.Produits>();
        }
        return this.produits;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="idReservation" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="dateReservation" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "idReservation",
        "dateReservation"
    })
    public static class AttributsReservation {

        @XmlElement(required = true)
        protected String idReservation;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dateReservation;

        /**
         * Gets the value of the idReservation property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdReservation() {
            return idReservation;
        }

        /**
         * Sets the value of the idReservation property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdReservation(String value) {
            this.idReservation = value;
        }

        /**
         * Gets the value of the dateReservation property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDateReservation() {
            return dateReservation;
        }

        /**
         * Sets the value of the dateReservation property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDateReservation(XMLGregorianCalendar value) {
            this.dateReservation = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="codeProduit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="tarif" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *         &lt;element name="quantite" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "codeProduit",
        "tarif",
        "quantite"
    })
    public static class Produits {

        @XmlElement(required = true)
        protected String codeProduit;
        protected double tarif;
        protected int quantite;

        /**
         * Gets the value of the codeProduit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodeProduit() {
            return codeProduit;
        }

        /**
         * Sets the value of the codeProduit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodeProduit(String value) {
            this.codeProduit = value;
        }

        /**
         * Gets the value of the tarif property.
         * 
         */
        public double getTarif() {
            return tarif;
        }

        /**
         * Sets the value of the tarif property.
         * 
         */
        public void setTarif(double value) {
            this.tarif = value;
        }

        /**
         * Gets the value of the quantite property.
         * 
         */
        public int getQuantite() {
            return quantite;
        }

        /**
         * Sets the value of the quantite property.
         * 
         */
        public void setQuantite(int value) {
            this.quantite = value;
        }

    }

}