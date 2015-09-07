package com.resanet.test;

import java.io.IOException;

import javax.jms.JMSException;

import org.junit.Test;

import com.resanet.esb.test.jms.JMSMessageSender;
import com.resanet.esb.test.util.FileResourceReader;

public class TesteurTP5a {

	@Test
	public void send() throws JMSException, IOException {
		String msg = FileResourceReader.read(("samples/TP5a_requete.xml"));
		JMSMessageSender.send("tcp://localhost:61617", "queue/tp5a/requete", msg);
	}
}
