package com.resanet.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;


public class Testeur {

	@Test
	public void send() throws JMSException, IOException {
		//String msg = read(("samples/requeteWeb.xml"));
		String msg = read(("samples/TP61_requete.xml"));
		send("tcp://localhost:61617", "in", "dsflkjdfjklg");
	}
	
	public static void send(String brokerURL, String destinationName, String msg) throws JMSException {
		ActiveMQConnectionFactory amqcf = new ActiveMQConnectionFactory(brokerURL);
		Connection connection = amqcf.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = new ActiveMQQueue(destinationName);
		MessageProducer messageProducer = session.createProducer(destination);
		Message message = session.createTextMessage(msg);
		messageProducer.send(message);

		messageProducer.close();
		session.close();
		connection.close();
	}

	public static void send(String brokerURL, String destinationName, String msg, Map<String, Object> headers)
			throws JMSException {
		ActiveMQConnectionFactory amqcf = new ActiveMQConnectionFactory(brokerURL);
		Connection connection = amqcf.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = new ActiveMQQueue(destinationName);
		MessageProducer messageProducer = session.createProducer(destination);
		Message message = session.createTextMessage(msg);

		for (Entry<String, Object> header : headers.entrySet()) {
			message.setObjectProperty(header.getKey(), header.getValue());
		}

		messageProducer.send(message);
		messageProducer.close();
		session.close();
		connection.close();
	}
	
	public static String read(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
}
