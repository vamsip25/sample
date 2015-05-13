package org.niit.guest.service.sample;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class TestClientTopicConsumer implements Runnable, ExceptionListener{
	
	private final Logger logger = Logger.getLogger(TestClientTopicConsumer.class.getName());
	
	private final String brokerUrl;
	private final String topicName;
	private final int lifetime;

	public TestClientTopicConsumer(String brokerUrl, String topicName, int lifetime)
	{
	    this.brokerUrl = brokerUrl;
	    this.topicName = topicName;
	    this.lifetime = lifetime;
	}

public void run()
{
    try
    {
        logger.log(Level.INFO, "Starting server on topic" + topicName + " using broker "+brokerUrl);

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

        Connection connection = connectionFactory.createConnection();
        connection.start();
        connection.setExceptionListener(this);

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(destination);

        long startTime = System.currentTimeMillis();

        while (true)
        {
            long now = System.currentTimeMillis();
            if (now - startTime > lifetime)
            {
            	logger.log(Level.INFO, "Time's up, exiting...");
                break;
            }

            // Wait for a message
            Message message = consumer.receive(1000);

            if (message == null)
                continue;

            if (message instanceof TextMessage)
            {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                
                logger.log(Level.INFO, "Received Message: "+text);
                
            }
            else
            {
            	logger.log(Level.INFO, "Received Message: "+message);
            }
        }

        consumer.close();
        session.close();
        connection.close();
    }
    catch (Exception e)
    {
    	logger.log(Level.SEVERE, "Exception while processing message: "+e.getMessage(), e);
        e.printStackTrace();
    }
}

	public synchronized void onException(JMSException ex)
	{
		logger.log(Level.SEVERE, "JMS Exception occured.  Shutting down client.", ex);
	}

	public static void main(String[] args) {
		
		
		String BROKER_URL = "tcp://localhost:61616";
		String topic = "PublishGuest";
		int CONSUME_LIFE_TIME_IN_MS = 3600 * 1000;
		TestClientTopicConsumer consumer = new TestClientTopicConsumer(BROKER_URL, topic, CONSUME_LIFE_TIME_IN_MS);
		
		Thread brokerThread = new Thread(consumer);
        brokerThread.setDaemon(false);
        brokerThread.start();
	}
}

