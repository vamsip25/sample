package org.niit.guest.service;

import org.niit.guest.dao.model.Guest;
import org.springframework.stereotype.Component;

@Component
public interface GuestService {

	Guest getGuestDetails(int guestId);
	int updateGuestDetails(int guestId, String lastName);

}
