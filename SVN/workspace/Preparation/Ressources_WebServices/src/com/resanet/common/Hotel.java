package com.resanet.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Hotel complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Hotel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hotel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ville" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nbEtoiles" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Hotel", propOrder = { "nom", "ville" })
public class Hotel {

	@XmlElement(name = "name", required = true)
	protected String nom;
	@XmlElement(name = "city", required = true)
	protected String ville;
	@XmlAttribute(name = "stars", required = true)
	protected int nbEtoiles;

	/**
	 * Gets the value of the hotel property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Sets the value of the hotel property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNom(String value) {
		this.nom = value;
	}

	/**
	 * Gets the value of the ville property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * Sets the value of the ville property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVille(String value) {
		this.ville = value;
	}

	/**
	 * Gets the value of the nbEtoiles property.
	 * 
	 */
	public int getNbEtoiles() {
		return nbEtoiles;
	}

	/**
	 * Sets the value of the nbEtoiles property.
	 * 
	 */
	public void setNbEtoiles(int value) {
		this.nbEtoiles = value;
	}

}
