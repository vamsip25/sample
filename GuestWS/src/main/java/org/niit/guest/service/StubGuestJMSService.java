package org.niit.guest.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class StubGuestJMSService implements GuestJMSService{
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	
	  public void sendText(final String text) {
	    this.jmsTemplate.convertAndSend(text);
	  }

	  public void send(final Destination dest,final String text) {
	     
	    this.jmsTemplate.send(dest,new MessageCreator() {
	      
	      public Message createMessage(Session session) throws JMSException {
	        Message message = session.createTextMessage(text);
	        return message;
	      }
	    });
	  }
}
