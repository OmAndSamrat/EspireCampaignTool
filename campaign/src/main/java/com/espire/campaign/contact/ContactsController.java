package com.espire.campaign.contact;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.espire.domain.Contact;

@Path("/contacts")
public class ContactsController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contact> searchContacts(
			@QueryParam("firstName")String firstName,@QueryParam("lastName")String lastName,
			@QueryParam("email") String email,@QueryParam("designationId") Long designationId,
			@QueryParam("orgId")Long orgId,@QueryParam("designationGrpId") Long designationGrpId,
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount
			){
		return null;
	}
	
}
