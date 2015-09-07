package com.resanet.eip.camel.transformations;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;

public class ClaimCheck extends RouteBuilder {

	private static final String LOG = "log:" + ClaimCheck.class + "?showAll=true&multiline=true";
	
	@Override
	public void configure() throws Exception {

		
		// claim check implémentation
		Database db = new Database();
		
		from("direct:claimCheck")
		.bean(new contentFilter(db))
		.to("direct:claimCheckInter")
		.bean(new contentEnricher(db))
		.to("direct:claimCheckOut");

		from("direct:claimCheckInter").to(LOG);
		from("direct:claimCheckOut").to(LOG);
	}

	/**
	 * Base de données non persistante très simpliste.
	 */
	static public class Database {
		private Map<String, Object> map = new HashMap<String, Object>();
		public void store(String key, Object value){map.put(key, value);}
		public Object get(String key){return map.get(key);}
	}
	
	/**
	 * Implémentation de l'EIP contentFilter.
	 */
	static public class contentFilter{
		private Database db;
		public contentFilter(Database db){this.db = db;}
		public void filtrer(Exchange ex, @Header("id") String id){
			db.store(id, ex.getIn().getBody());
			ex.getIn().setBody(null);
		}
	}
	
	/**
	 * Implémentation de l'EIP contentEnricher.
	 */
	static public class contentEnricher{
		private Database db;
		public contentEnricher(Database db){this.db = db;}
		public void filtrer(Exchange ex, @Header("id") String id){
			ex.getIn().setBody(db.get(id));
		}
	}
}
