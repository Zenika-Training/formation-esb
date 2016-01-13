package com.resanet.esb.tp11.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandeService {

    private static final Logger LOG = LoggerFactory.getLogger(CommandeService.class);

    public String receptionCommande() throws Exception {
        LOG.info("Je commence a traiter la commande...");
        Thread.sleep(10000);

        int hasard = (int)(Math.random()*100);

        if (hasard%2==0) {
            throw new Exception("Erreur de traitement !!!");
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("<resultatComplexe xmlns=\"http://esb.resanet.com/pi\">");
        sb.append(Math.sqrt(Math.PI));
        sb.append("</resultatComplexe>");

        LOG.info("J'ai fini de traiter la commande...");
        return sb.toString();
    }
}
