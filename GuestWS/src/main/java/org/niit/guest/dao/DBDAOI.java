package org.niit.guest.dao;

import org.niit.guest.dao.model.Guest;

public interface DBDAOI {

	public Guest getGuestDetails(int gid);
	public int updateLastName(int gid, String newLastName);
}
