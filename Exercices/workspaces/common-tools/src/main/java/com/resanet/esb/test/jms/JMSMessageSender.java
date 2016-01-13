package com.resanet.esb.test.jms;

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

public class JMSMessageSender {

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
	
	
	public static void main(String[] args) throws JMSException {
		send("tcp://localhost:61617","DSKFJSFJKLSJLDFJFD","mon message");
	}

}
