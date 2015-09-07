package com.resanet.camel_su.catalogue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Catalogue {

	/** Helper JDBC. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOG = LoggerFactory.getLogger(Catalogue.class);

	private final static String DROP = "DROP TABLE IF EXISTS code_tarifaire";
	private final static String CREATE = "CREATE TABLE code_tarifaire ( code VARCHAR(256) , tarif DOUBLE)";
	private final static String INSERT_1 = "INSERT INTO code_tarifaire(code,tarif) VALUES('B', 22.5)";
	private final static String INSERT_2 = "INSERT INTO code_tarifaire(code,tarif) VALUES('R', 30.0)";

	/** Selection des tarifs. */
	public final static String SELECT = "SELECT tarif FROM code_tarifaire WHERE code = ?";

	/**
	 * Creation et chargement de la base.
	 */
    @PostConstruct
	public void init() {
        LOG.info("init : {}", jdbcTemplate);
		jdbcTemplate.execute(DROP);
		jdbcTemplate.execute(CREATE);
		jdbcTemplate.execute(INSERT_1);
		jdbcTemplate.execute(INSERT_2);
		LOG.info("Initialisation du Catalogue terminee.");
	}
}
