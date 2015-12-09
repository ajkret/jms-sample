package com.cinq.hr.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.stereotype.Component;

@Component
public class Client implements ExceptionListener {
//	public static void main(String[] args) {
//		byte b[] = new byte[256];
//		System.out.print("Greet someone: ");
//		try {
//			int i = System.in.read(b);
//			String greeting = new String(b, 0, i);
//			Client client = new Client();
//			client.sendAMessage(greeting);
//		} catch (Exception e) {
//			System.out.println("Type something or 'quit'");
//		}
//	}

	public boolean sendAMessage(String greeting) {
		try {

			Properties jndiParameters = new Properties();
			jndiParameters.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			jndiParameters.put(Context.PROVIDER_URL, "tcp://localhost:61616");
			Context context = new InitialContext(jndiParameters);
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Destination queue = (Destination) context.lookup("dynamicQueues/Greeting");

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			connection.setExceptionListener(this);

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create a MessageConsumer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			String text = greeting + " From: " + Thread.currentThread().getName() + " : " + this.hashCode();
			TextMessage message = session.createTextMessage(text);

			producer.send(message);

			producer.close();
			session.close();
			connection.close();
			return true;
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
			return false;
		}
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("JMS Exception occured.");
	}

}
