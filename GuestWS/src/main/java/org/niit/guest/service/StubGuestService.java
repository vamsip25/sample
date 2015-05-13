package org.niit.guest.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.niit.guest.dao.DBDAOI;
import org.niit.guest.dao.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service 
public class StubGuestService implements GuestService{
	private final Logger logger = Logger.getLogger(StubGuestService.class.getName());
	
	private DBDAOI dbDao;
	private GuestJMSService guestJMSService;
	
	@Autowired
    public void setDbDao(DBDAOI dbDao) {
        this.dbDao = dbDao;
    }
	
	@Autowired
    public void setGuestJMSService(GuestJMSService guestJMSService) {
        this.guestJMSService = guestJMSService;
    }
	
	public Guest getGuestDetails(int guestId){
	
		logger.log(Level.FINE, "Invoked getGuestDetails: guestId: "+guestId);
		
		return dbDao.getGuestDetails(guestId);
	}
	
	public int updateGuestDetails(int guestId, String lastName){

		logger.log(Level.FINE,"Invoked updateGuestDetails: guestDetails: Id: "+guestId+" newLastName: "+lastName);
		
		int retVal = dbDao.updateLastName(guestId, lastName);
			if(retVal > 0){
				retVal = 0;
				logger.log(Level.FINEST,"Now publishing to JMS topic..");
				guestJMSService.sendText("Successfully updated Last name: "+lastName+" for guest: "+guestId);
				logger.log(Level.FINEST,"Now publishing to JMS topic.. Done");
				
			}else{
				retVal = -1;
			}
				
		return retVal;
	}

}
