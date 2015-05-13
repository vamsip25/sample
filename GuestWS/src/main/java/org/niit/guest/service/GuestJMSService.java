package org.niit.guest.service;

import javax.jms.Destination;

public interface GuestJMSService {
	void sendText(String message);
	void send(final Destination dest,final String text);
}
