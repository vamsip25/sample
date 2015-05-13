package org.niit.guest.dao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestClass {

	public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "spring-ws-servlet.xml");
 
        DBDAO dbDao = ctx.getBean("dbDao",
                DBDAO.class);
 
        dbDao.getGuestDetails(1);
        
        dbDao.updateLastName(1, "PVK");
        
        ctx.close();
    }
}
