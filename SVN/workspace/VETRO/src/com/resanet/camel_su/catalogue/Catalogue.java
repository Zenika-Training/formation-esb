package com.resanet.camel_su.catalogue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class Catalogue {

	/** Helper JDBC. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Log LOG = LogFactory.getLog(Catalogue.class);

	private final static String CREATE = "CREATE TABLE code_tarifaire ( code VARCHAR(256) , tarif DOUBLE)";
	private final static String INSERT_1 = "INSERT INTO code_tarifaire(code,tarif) VALUES('B', 22.5)";
	private final static String INSERT_2 = "INSERT INTO code_tarifaire(code,tarif) VALUES('R', 30.0)";

	/** Sélection des tarifs. */
	public final static String SELECT = "SELECT tarif FROM code_tarifaire WHERE code = ?";

	/**
	 * Création et chargement de la base.
	 */
	public void init() {
		jdbcTemplate.execute(CREATE);
		jdbcTemplate.execute(INSERT_1);
		jdbcTemplate.execute(INSERT_2);
		LOG.info("Initialisation du Catalogue terminee.");
	}
}
