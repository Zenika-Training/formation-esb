package com.resanet.jmx;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.junit.Test;

public class ClientJMX {

	@Test
	public void test() {

		JMXConnector jmxc = null;
		try {
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
			Map<String, Object> environment = new HashMap<String, Object>();
			String[] credentials = new String[] { "smx", "smx" };
			environment.put(JMXConnector.CREDENTIALS, credentials);
			jmxc = JMXConnectorFactory.connect(url, environment);

			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			ObjectName mbeanName = new ObjectName(
					"org.apache.activemq:BrokerName=localhost,Type=Queue,Destination=file.demande.informations");

			System.out.println("NOMBRE DE REQUETES EN ENTREE : " + mbsc.getAttribute(mbeanName, "EnqueueCount"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jmxc != null) {
				try {
					jmxc.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
