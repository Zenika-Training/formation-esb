package com.resanet.ws_bean_su;

import java.io.StringWriter;
import java.util.Set;

import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.servicemix.JbiConstants;
import org.apache.servicemix.jbi.listener.MessageExchangeListener;

public class MessageBean implements MessageExchangeListener {

	@Resource
	private DeliveryChannel channel;

	public void onMessageExchange(MessageExchange exchange) throws MessagingException {
		if (exchange.getStatus() != ExchangeStatus.ACTIVE) {
			return;
		}

		MessageExchange exch = channel.createExchangeFactoryForService(
				new QName("http://www.resanet.partenaires.com/hotel", "camelReceiver")).createInOutExchange();
		exch.setMessage(exchange.getMessage("in"), "in");
		exch.setProperty(JbiConstants.SENDER_ENDPOINT, exchange.getProperty(JbiConstants.SENDER_ENDPOINT));

		channel.sendSync(exch);
		exch.setStatus(ExchangeStatus.DONE);
		channel.send(exch);

		exchange.setMessage(exch.getMessage("out"), "out");
		channel.send(exchange);

		logEchange("ECHANGE PRINCIPAL", exch);
		System.out.println("////////////////////////////");
		logMessage("MESSAGE ENTREE", exch.getMessage("in"));
		System.out.println("////////////////////////////");
		logMessage("MESSAGE SORTIE", exch.getMessage("out"));
	}

	@SuppressWarnings("unchecked")
	private void logEchange(String prefixe, MessageExchange exchange) {
		System.out.println("[" + prefixe + "]");
		Set<String> cles = (Set<String>) exchange.getPropertyNames();
		for (String cle : cles) {
			System.out.println("PROPRIETE " + cle + ":" + exchange.getProperty(cle));
		}
	}

	@SuppressWarnings("unchecked")
	private void logMessage(String prefixe, NormalizedMessage message) {
		System.out.println("[" + prefixe + "]");
		Set<String> cles = (Set<String>) message.getPropertyNames();
		for (String cle : cles) {
			System.out.println("PROPRIETE " + cle + ":" + message.getProperty(cle));
		}
		System.out.println("CONTENU :");
		System.out.println(documentToString((DOMSource) message.getContent()));
	}

	private String documentToString(DOMSource source) {
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
}
