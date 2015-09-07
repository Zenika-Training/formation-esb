package com.resanet.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.junit.Test;

import com.resanet.esb.test.jms.JMSMessageSender;
import com.resanet.esb.test.util.FileResourceReader;

public class TesteurTP5b {

	@Test
	public void send() throws JMSException, IOException {
		String msg = FileResourceReader.read(("samples/TP5b_requete.xml"));
		Map<String, Object> headers = new HashMap<String, Object>(1);
		headers.put("typeMessage", "widget");
		JMSMessageSender.send("tcp://localhost:61617", "queue/tp5b/requete", msg, headers);

		headers = new HashMap<String, Object>(1);
		headers.put("typeMessage", "gadget");
		JMSMessageSender.send("tcp://localhost:61617", "queue/tp5b/requete", msg, headers);
	}
}
