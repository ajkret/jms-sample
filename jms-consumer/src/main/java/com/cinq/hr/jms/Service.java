package com.cinq.hr.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Service implements Runnable, ExceptionListener {
	public static void main(String args[]) {
		System.out.println("Send the messages....");
		Thread thread = new Thread(new Service());
		thread.start();
	}

	public void run() {
		try {

			// Create a ConnectionFactory
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

			//			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create a MessageConsumer from the Session to the Topic or Queue
			MessageConsumer consumer = session.createConsumer(queue);

			boolean dontQuit = true;
			do {
				// Wait for a message
				Message message = consumer.receive();

				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println("Received: " + text);
					if (text != null && text.startsWith("quit")) {
						dontQuit = false;
					}
				} else {
					System.out.println("Received: " + message);
				}
			} while (dontQuit);

			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("JMS Exception occured.  Shutting down client.");
	}
}