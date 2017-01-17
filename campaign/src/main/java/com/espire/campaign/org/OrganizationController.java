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

import com.espire.campaign.exception.DBException;
import com.espire.campaign.org.service.OrganizationService;
import com.espire.domain.Organization;

@Path("/organizations")
public class OrganizationController {
	
	@EJB
	OrganizationService orgService;
	
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
	
	@POST
	@RolesAllowed({"IS","MARKETING"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrganization(@Context SecurityContext sc, @Valid Organization org){
		log.info("com.espire.campaign.org.OrganizationController.createOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		Organization createdOrg = orgService.createOrganization(org);
		log.info("Created organization "+org.toString());
		return Response.status(Status.CREATED).entity(createdOrg).build();
	}
	
	@Path("/{Id}")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganization(@Context SecurityContext sc,@PathParam("Id") Long organizationId){
		log.info("com.espire.campaign.org.OrganizationController.getOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		Organization foundOrg =  orgService.getOrganizationById(organizationId);
		if(foundOrg!=null){
			return Response.status(Status.OK).entity(foundOrg).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{Id}")
	@PUT
	@RolesAllowed({"IS","MARKETING"})
	public Response updateOrganization(@Context SecurityContext sc,@PathParam("Id") Long organizationId, @Valid Organization org){
		log.info("com.espire.campaign.org.OrganizationController.updateOrganization INVOKED BY" +sc.getUserPrincipal().getName());
		try{
			orgService.updateOrganization(organizationId ,org);
		}catch(DBException dbe){
			return Response.status(Status.NOT_FOUND).build();
		}
		return  Response.status(Status.NO_CONTENT).build();
	}
	
}
