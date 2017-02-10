package com.espire.campaign.contact;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.espire.campaign.contact.service.ContactService;
import com.espire.domain.Contact;

@Path("/contacts")
public class ContactsController {
	
	@EJB
	private ContactService contactService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contact> searchContacts(
			@QueryParam("firstName")String firstName,@QueryParam("lastName")String lastName,
			@QueryParam("email") String email,@QueryParam("designationId") Long designationId,
			@QueryParam("orgId")Long orgId,@QueryParam("designationGrpId") Long designationGrpId,
			@QueryParam("gender") String gender,@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount
			){
		return contactService.searchContacts(firstName, lastName, email, designationId, orgId, designationGrpId,gender, resultIndex, resultCount);
	}
	
	@Path("/{campaignId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contact> searchCampaignNonAssignedContacts(
			@QueryParam("firstName")String firstName,@QueryParam("lastName")String lastName,
			@QueryParam("email") String email,@QueryParam("designationId") Long designationId,
			@QueryParam("orgId")Long orgId,@QueryParam("designationGrpId") Long designationGrpId,
			@QueryParam("gender") String gender,@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount,
			@PathParam("campaignId") Long campaignId
			){
		return contactService.searchCampaignNonAssignedContacts(firstName, lastName, email, designationId, orgId, designationGrpId,gender, resultIndex, resultCount, campaignId);
	}
	
}
