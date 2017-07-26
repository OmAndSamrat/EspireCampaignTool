package com.espire.campaign.org;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import com.espire.campaign.contact.service.ContactService;
import com.espire.campaign.exception.DBException;
import com.espire.campaign.org.service.OrganizationService;
import com.espire.domain.Contact;
import com.espire.domain.Organization;

@Path("/organizations")
public class OrganizationController {
	
	@EJB
	private OrganizationService orgService;
	
	@EJB
	private ContactService contService;
	
	final static Logger log = Logger.getLogger(OrganizationController.class);	

	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response listOrganzations(@Context SecurityContext sc,
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount){
		log.info("com.espire.campaign.org.OrganizationController.listOrganzations INVOKED BY" +sc.getUserPrincipal().getName());
		log.info("caling listOrganizations index:"+resultIndex+" count:"+resultCount);
		
		if(resultIndex==null ^ resultCount==null){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(orgService.listOrganzations(resultIndex, resultCount)).build();
	}
	@Path("/search")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchOrganzations(@Context SecurityContext sc, @QueryParam("orgName")String orgName,@QueryParam("domain")Long domain,
			@QueryParam("geo") Long geo){
		log.info("com.espire.campaign.org.OrganizationController.searchOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		log.info("com.espire.campaign.org.OrganizationController.searchOrganization");
		return Response.status(Status.OK).entity(orgService.searchOrganzations(orgName, domain, geo)).build();
	}
	
	@POST
	@RolesAllowed({"IS","MARKETING"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrganization(@Context SecurityContext sc, @Valid Organization org){
		log.info("com.espire.campaign.org.OrganizationController.createOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		if(org.getOrganisationID()!=null){
			return Response.status(Status.BAD_REQUEST).entity("{\"error\":\"ID cannot be sent while creating an entity\"}").build();
		}
		Organization createdOrg = orgService.createOrganization(org);
		if(createdOrg ==null){
			Response.status(Status.BAD_REQUEST).build();
		}
		log.info("Created organization "+org.toString());
		return Response.status(Status.CREATED).entity(createdOrg).build();
	}
	
	@Path("/{Id}")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganization(@Context SecurityContext sc,@PathParam("Id") Long organizationId){
		log.info("com.espire.campaign.org.OrganizationController.getOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		Organization found =  orgService.getOrganizationById(organizationId);
		if(found!=null){
			return Response.status(Status.OK).entity(found).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{Id}")
	@PUT
	@RolesAllowed({"IS","MARKETING"})
	public Response updateOrganization(@Context SecurityContext sc,@PathParam("Id") Long organizationId, @Valid Organization org){
		log.info("OrganizationController.updateOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		try{
			orgService.updateOrganization(organizationId ,org);
		}catch(DBException dbe){
			return Response.status(Status.NOT_FOUND).build();
		}
		return  Response.status(Status.OK).entity(org).build();
	}
	
	@Path("/{Id}/contacts")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContactsByOrganization(@Context SecurityContext sc,@PathParam("Id") Long organizationId ){
		log.info("OrganizationController.getContactsByOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		
		return Response.status(Status.OK).entity(contService.getContactByOrganization(organizationId)).build();
	}
	
	@Path("/{Id}/contacts")
	@POST
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createContactsinOrganization(@Context SecurityContext sc,@PathParam("Id") Long organizationId,@Valid Contact contact ){
		log.info("OrganizationController.getContactsByOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		if(contact.getContactID()!=null){
			return Response.status(Status.BAD_REQUEST).entity("{\"error\":\"ID cannot be sent while creating an entity\"}").build();
		}
		Contact created = contService.createContactInOraganzation(organizationId,contact);
		if(created ==null){
			Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.CREATED).entity(created).build();
	}
	
	
	
}
