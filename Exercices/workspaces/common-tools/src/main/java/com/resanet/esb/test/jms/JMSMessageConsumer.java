package com.resanet.esb.test.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class JMSMessageConsumer {

	public static void consume(String brokerURL, String destinationName) throws JMSException {
		ActiveMQConnectionFactory amqcf = new ActiveMQConnectionFactory(brokerURL);
		Connection connection = amqcf.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = new ActiveMQQueue(destinationName);
		MessageConsumer messageConsumer = session.createConsumer(destination);
		Message message = messageConsumer.receive(5000);
		if (message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
			System.out.println(tm.getText());
		}

		messageConsumer.close();
		session.close();
		connection.close();
	}
}
