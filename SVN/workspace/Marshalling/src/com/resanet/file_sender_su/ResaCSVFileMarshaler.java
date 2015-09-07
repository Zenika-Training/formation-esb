package com.resanet.file_sender_su;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.servicemix.components.util.DefaultFileMarshaler;

import com.resanet.data.JAXBUtil;
import com.resanet.data.ObjectFactory;
import com.resanet.data.Reservation;

public class ResaCSVFileMarshaler extends DefaultFileMarshaler {

	private static final Log LOG = LogFactory.getLog(ResaCSVFileMarshaler.class);


	/** Prefixe du fichier. */
	private String prefixe;

	/** Extension du fichier. */
	private String extension;

	/** Séquence interne. */
	private static long sequence = 1;

	/**
	 * Surcharge pour formatter le nom du fichier sur des règles métiers.
	 * 
	 * @param exchange l'echange JBI
	 * @param message le message JBI
	 */
	@Override
	public String getOutputName(MessageExchange exchange, NormalizedMessage message) throws MessagingException {
		StringBuilder sb = new StringBuilder(prefixe);
		sb.append(++sequence);
		sb.append("-");
		sb.append(new SimpleDateFormat("yyyy-MM-DD-HH-mm-SS-sss").format(new Date()));
		sb.append(extension);

		LOG.info("Nom de fichier : " + sb.toString());
		return sb.toString();
	}

	/**
	 * Surcharge pour formatter le XML JBI vers un format métier (CSV).
	 * 
	 * @param exchange l'échange JBI
	 * @param nm le message JBI
	 * @param os le flux d'écriture
	 * @param path chemin du fichier
	 */
	@Override
	protected void writeMessageContent(MessageExchange exchange, NormalizedMessage nm, OutputStream os, String path)
			throws MessagingException {

		try {

			Reservation r = (Reservation) JAXBUtil.unmarshall(nm.getContent(), ObjectFactory.class);
			os.write(r.toCSV().getBytes("UTF-8"));

			LOG.info("Reservation unmarshall vers CSV ");
			LOG.info(r.toCSV());

		} catch (JAXBException jaxbe) {
			LOG.error("Erreur au mapping XML->Objet JAXB. Motif " + jaxbe.getMessage(), jaxbe);
			throw new MessagingException("Erreur au mapping XML->Objet JAXB. Motif " + jaxbe.getMessage(), jaxbe);
		} catch (IOException ioe) {
			LOG.error("Erreur a l'ecriture du fichier. Motif " + ioe.getMessage(), ioe);
			throw new MessagingException("Erreur a l'ecriture du fichier. Motif " + ioe.getMessage(), ioe);
		}
	}

	public void setPrefixe(String prefixe) {
		this.prefixe = prefixe;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
