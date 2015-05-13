package org.niit.guest.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.niit.guest.dao.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class DBDAO implements DBDAOI{

	private final Logger logger = Logger.getLogger(DBDAO.class.getName());
	private JdbcTemplate jdbcTemplate;
	
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Guest getGuestDetails(int gid){
    	SqlRowSet row = jdbcTemplate.queryForRowSet("select * from guest where guest_id = "+gid);
    	Guest retGuest;
    	
    	if(row.next()){
	    	
    		String firstName = row.getString(2);
	    	String lastName = row.getString(3);
	    	retGuest = new Guest(row.getInt(1), row.getString(2), row.getString(3), 1);
	    	logger.log(Level.FINE, "gid: "+gid+" fname: "+firstName+" lastName: "+lastName);
	    	
	    } else {
	    	retGuest = new Guest(-1);
	    }
    	
    	return retGuest;
    }
    
    public int updateLastName(int gid, String newLastName){
    	int retVal = -1;
    	try{ 
    		
    		retVal = jdbcTemplate.update("update guest set last_name = ? where guest_id = ?", newLastName, gid);
    		if(retVal > 0){
    			logger.log(Level.FINE, "Update successful: "+retVal);
    		}else{
    			logger.log(Level.FINE,"Unknown error occured, unable to update record");
    		}
    	}catch(Exception e){
    		logger.log(Level.SEVERE, "Database Exception: "+e.getMessage(), e);
    	}
    	
    	return retVal;
    }

}
