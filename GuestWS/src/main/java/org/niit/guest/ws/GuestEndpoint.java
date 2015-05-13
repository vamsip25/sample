package org.niit.guest.ws;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPath;
import org.niit.guest.dao.model.Guest;
import org.niit.guest.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class GuestEndpoint {

	private final Logger logger = Logger.getLogger(GuestEndpoint.class.getName());
	
	private static final String NAMESPACE_URI = "http://test.niit.org/guest/schemas";
	private GuestService guestService;
	
	private XPath guestIDExpression;
	private XPath guestLNameExpression;
	
	@Autowired
	public GuestEndpoint(GuestService guestService) throws Exception{
		this.guestService = guestService;
		Namespace namespace = Namespace.getNamespace("gt", NAMESPACE_URI);
		guestIDExpression = XPath.newInstance("//gt:GuestId");
		guestIDExpression.addNamespace(namespace);
		
		guestLNameExpression = XPath.newInstance("//gt:NewLastName");
		guestLNameExpression.addNamespace(namespace);
		
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GuestDetailsRequest")
	@ResponsePayload 
	public Element getGuestDetails(@RequestPayload Element guestDetailsRequest) throws Exception{
		
		Number guestId = guestIDExpression.numberValueOf(guestDetailsRequest);
				
		Guest guest = guestService.getGuestDetails(guestId.intValue());

		if(guest == null){
			guest = new Guest(-1);
		}
		
		Element root = new Element("GuestDetailsResponse");

		Element gid = new Element("GuestID");
		gid.setText(String.valueOf(guest.getGuestId()));
		root.addContent(gid);
		
		Element fname = new Element("FirstName");
		fname.setText(guest.getFirstName());
		root.addContent(fname);
		
		Element lname = new Element("LastName");
		lname.setText(guest.getLastName());
		root.addContent(lname);
		
        return root;
		
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GuestLNameChangeRequest")
	@ResponsePayload 
	public Element updateGuestDetails(@RequestPayload Element guestDetailsRequest) throws Exception{
		
		logger.log(Level.FINE,	"Request Element: "+guestDetailsRequest);
		Number guestId = guestIDExpression.numberValueOf(guestDetailsRequest);
		String guestNewLName = guestLNameExpression.valueOf(guestDetailsRequest);
		
		logger.log(Level.INFO, "Updating Guest Information: Guest ID: "+guestId+" Guest new LName: "+guestNewLName);
		int ret = guestService.updateGuestDetails(guestId.intValue(), guestNewLName);

		String statusCode = "";
		String statusDesc = "";
		
		if(ret >= 0){
			statusCode = "0";
			statusDesc = "Updated Successfully";
			logger.log(Level.INFO, "Updated Guest Information: Guest ID: "+guestId+" Guest new LName: "+guestNewLName);
		}else {
			statusCode = String.valueOf(ret);
			statusDesc = "Unable to update";
			logger.log(Level.INFO, "Unable to update Guest Information: Guest ID: "+guestId+" Guest new LName: "+guestNewLName);
		}

		Element root = new Element("GuestLNameChangeResponse");

		Element gid = new Element("GuestId");
		gid.setText(String.valueOf(guestId.intValue()));
		root.addContent(gid);
		
		Element fname = new Element("StatusCode");
		fname.setText(statusCode);
		root.addContent(fname);
		
		Element lname = new Element("StatusDesc");
		lname.setText(statusDesc);
		root.addContent(lname);

		return root;
	}
}
