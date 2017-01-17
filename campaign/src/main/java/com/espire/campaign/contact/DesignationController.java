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

import com.espire.campaign.contact.service.DesignationService;
import com.espire.campaign.exception.DBException;
import com.espire.domain.Designation;

@Path("/designations")
public class DesignationController {

	@EJB
	DesignationService designationService;
	
	final static Logger log = Logger.getLogger(DesignationController.class);	

	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response listDesignations(@Context SecurityContext sc,
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount){
		log.info(" DesignationController.listDesignations INVOKED BY " +sc.getUserPrincipal().getName());
		log.info("caling listDesignations index:"+resultIndex+" count:"+resultCount);
		
		if(resultIndex==null ^ resultCount==null){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(designationService.listDesignations(resultIndex, resultCount)).build();
	}
	
	@POST
	@RolesAllowed({"IS","MARKETING"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDesignation(@Context SecurityContext sc, @Valid Designation designation){
		log.info(" DesignationController.createDesignation INVOKED BY " +sc.getUserPrincipal().getName());
		Designation createdDesig = designationService.createDesignation(designation);
		log.info("Created Designation "+designation.toString());
		return Response.status(Status.CREATED).entity(createdDesig).build();
	}
	
	@Path("/{Id}")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDesignation(@Context SecurityContext sc,@PathParam("Id") Long DesignationId){
		log.info(" DesignationController.getDesignation INVOKED BY " +sc.getUserPrincipal().getName());
		Designation found =  designationService.getDesignationById(DesignationId);
		if(found!=null){
			return Response.status(Status.OK).entity(found).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{Id}")
	@PUT
	@RolesAllowed({"IS","MARKETING"})
	public Response updateDesignation(@Context SecurityContext sc,@PathParam("Id") Long DesignationId, @Valid Designation desig){
		log.info(" DesignationController.updateDesignation INVOKED BY " +sc.getUserPrincipal().getName());
		try{
			designationService.updateDesignation(DesignationId ,desig);
		}catch(DBException dbe){
			return Response.status(Status.NOT_FOUND).build();
		}
		return  Response.status(Status.NO_CONTENT).build();
	}
	
}
