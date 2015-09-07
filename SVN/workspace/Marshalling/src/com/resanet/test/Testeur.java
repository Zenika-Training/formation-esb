package com.resanet.test;

import java.io.IOException;

import javax.jms.JMSException;

import org.junit.Test;

import com.resanet.esb.test.jms.JMSMessageSender;
import com.resanet.esb.test.util.FileResourceReader;

public class Testeur {

	@Test
	public void send() throws JMSException, IOException {

		String msg = FileResourceReader.read(("samples/reservations.xml"));
		JMSMessageSender.send("tcp://localhost:61617", "queue/resanet/reservation_vers_airalpha_tp4", msg);

	}
}
