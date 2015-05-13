package org.niit.guest.dao.model;

public class Guest {

	private int guestId;
	private String firstName;
	private String lastName;
	private int status;
	
	public Guest(int status){
		this.status = status;
		this.guestId = -1;
		this.firstName = "NOT_FOUND";
		this.lastName = "NOT_FOUND";
	}
	
	public Guest(int guestId, String firstName, String lastName, int status){
		this.guestId = guestId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
	}

	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
