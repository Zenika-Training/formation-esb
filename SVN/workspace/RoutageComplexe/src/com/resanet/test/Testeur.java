package com.resanet.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.junit.Test;

import com.resanet.esb.test.jms.JMSMessageSender;
import com.resanet.esb.test.util.FileResourceReader;

public class Testeur {

	@Test
	public void send() throws JMSException, IOException {

		String msg = FileResourceReader.read(("samples/TP61_requete.xml"));
		Map<String, Object> headers = new HashMap<String, Object>(1);

		// envoi de la requête normale
		long correlationID = System.currentTimeMillis();
		String parite = (correlationID % 2 == 0) ? "pair" : "impair";
		System.out.println(">>> correlationID est " + parite);
		headers.put("correlation_id", correlationID);
		JMSMessageSender.send("tcp://localhost:61617", "queue/resanet/routageComplexe/requete", msg, headers);

		// envoi de la notification
		msg = FileResourceReader.read(("samples/TP61_notification.xml"));
		JMSMessageSender.send("tcp://localhost:61617", "queue/resanet/routageComplexe/requete", msg);
	}
}
