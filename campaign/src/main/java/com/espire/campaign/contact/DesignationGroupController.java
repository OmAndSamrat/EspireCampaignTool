package com.espire.campaign.contact;

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

import com.espire.campaign.contact.service.DesignationGroupService;
import com.espire.campaign.exception.DBException;
import com.espire.domain.DesignationGroup;

@Path("/designationgroups")
public class DesignationGroupController {

	@EJB
	private DesignationGroupService dgService;
	
	final static Logger log = Logger.getLogger(DesignationGroupController.class);	

	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response listDesignationGroups(@Context SecurityContext sc,
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount){
		log.info(" DesignationGroupController.listDesignationGroups INVOKED BY " +sc.getUserPrincipal().getName());
		log.info("caling listDesignationGroups index:"+resultIndex+" count:"+resultCount);
		
		if(resultIndex==null ^ resultCount==null){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(dgService.listDesignationGroups(resultIndex, resultCount)).build();
	}
	
	@POST
	@RolesAllowed({"IS","MARKETING"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDesignationGroup(@Context SecurityContext sc, @Valid DesignationGroup dom){
		log.info(" DesignationGroupController.createDesignationGroup INVOKED BY " +sc.getUserPrincipal().getName());
		DesignationGroup created = dgService.createDesignationGroup(dom);
		if(created ==null){
			Response.status(Status.BAD_REQUEST).build();
		}
		log.info("Created DesignationGroup "+dom.toString());
		return Response.status(Status.CREATED).entity(created).build();
	}
	
	@Path("/{Id}")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDesignationGroup(@Context SecurityContext sc,@PathParam("Id") Long DesignationGroupId){
		log.info(" DesignationGroupController.getDesignationGroup INVOKED BY " +sc.getUserPrincipal().getName());
		DesignationGroup found =  dgService.getDesignationGroupById(DesignationGroupId);
		if(found!=null){
			return Response.status(Status.OK).entity(found).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{Id}")
	@PUT
	@RolesAllowed({"IS","MARKETING"})
	public Response updateDesignationGroup(@Context SecurityContext sc,@PathParam("Id") Long DesignationGroupId, @Valid DesignationGroup dg){
		log.info(" DesignationGroupController.updateDesignationGroup INVOKED BY " +sc.getUserPrincipal().getName());
		try{
			dgService.updateDesignationGroup(DesignationGroupId ,dg);
		}catch(DBException dbe){
			return Response.status(Status.NOT_FOUND).build();
		}
		return  Response.status(Status.NO_CONTENT).build();
	}
	
}
