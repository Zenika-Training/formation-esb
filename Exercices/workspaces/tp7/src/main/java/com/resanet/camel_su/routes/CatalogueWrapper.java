package com.resanet.camel_su.routes;

import com.resanet.camel_su.catalogue.Catalogue;
import com.resanet.camel_su.oxm.JAXBUtil;
import com.resanet.camel_su.oxm.reservationResa.ReservationResa;
import com.resanet.camel_su.oxm.reservationResa.ReservationResa.AttributsReservation;
import com.resanet.camel_su.oxm.reservationResa.ReservationResa.Produits;
import com.resanet.camel_su.oxm.reservationWeb.ObjectFactory;
import com.resanet.camel_su.oxm.reservationWeb.ReservationWeb;
import com.resanet.camel_su.oxm.reservationWeb.ReservationWeb.Produit;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;

@Service
public class CatalogueWrapper {

	/** Le logger. */
	private static final Log LOG = LogFactory.getLog(CatalogueWrapper.class);

	/** Helper JDBC. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Enrichissement du body In avec apport catalogue.
	 * 
	 * @param bodyWeb le body initial
	 * @param exchange l'echange Camel
	 * 
	 * @throws Exception si erreur
	 */
	public void enrichirEtTransformer(@Body Source bodyWeb, Exchange exchange) throws Exception {

		try {

			ReservationWeb resaWeb = (ReservationWeb) JAXBUtil.unmarshall(bodyWeb, ObjectFactory.class);

			com.resanet.camel_su.oxm.reservationResa.ObjectFactory of = new com.resanet.camel_su.oxm.reservationResa.ObjectFactory();
			ReservationResa resaResa = of.createReservation();

			AttributsReservation ar = of.createReservationAttributsReservation();
			resaResa.setAttributsReservation(ar);
			ar.setDateReservation(resaWeb.getDateReservation());
			ar.setIdReservation(resaWeb.getIdReservation());

			for (Produit p : resaWeb.getProduit()) {
				Produits produits = of.createReservationProduits();
				resaResa.getProduits().add(produits);
				produits.setCodeProduit(p.getCodeProduit());
				produits.setQuantite(p.getQuantite());
				produits.setTarif(getTarifByCodeTarifaire(p.getCodeTarifaire()));
			}

			Source bodyResa = JAXBUtil.marshall(resaResa, of.getClass());
			exchange.getIn().setBody(bodyResa, Source.class);

		} catch (JAXBException jaxbe) {
			LOG.error("Erreur irrecuperable au mapping OXM JAXB.", jaxbe);
			throw new Exception("Erreur irrecuperable au mapping OXM JAXB.", jaxbe);
		}
	}

	/**
	 * Retourne le tarif a partir d'un code tarifaire.
	 * 
	 * @param codeTarifaire le code tarifaire
	 * 
	 * @return le tarif sous forme de double
	 */
	private double getTarifByCodeTarifaire(String codeTarifaire) {
		try {
			return (Double) jdbcTemplate.queryForObject(Catalogue.SELECT, new Object[] { codeTarifaire }, Double.class);
		} catch (Exception e) {
			LOG.info("Code tarifaire " + codeTarifaire + " non trouve");
			return 0.0;
		}
	}
}
