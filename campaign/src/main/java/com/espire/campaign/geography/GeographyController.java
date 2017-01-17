package com.espire.campaign.geography;

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
import com.espire.campaign.geography.service.GeographyService;
import com.espire.domain.Geography;

@Path("/geographies")
public class GeographyController {
	
	@EJB
	private GeographyService geoService;
	
	final static Logger log = Logger.getLogger(GeographyController.class);	

	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGeographys(@Context SecurityContext sc,
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount){
		log.info(" GeographyController.listOrganzations INVOKED  BY: " +sc.getUserPrincipal().getName());
		log.info("caling listGeographys index:"+resultIndex+" count:"+resultCount);
		
		if(resultIndex==null ^ resultCount==null){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(geoService.listGeographies(resultIndex, resultCount)).build();
	}
	
	@POST
	@RolesAllowed({"IS","MARKETING"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGeography(@Context SecurityContext sc, @Valid Geography geo){
		log.info(" GeographyController.createGeography INVOKED  BY: " +sc.getUserPrincipal().getName());
		Geography createdOrg = geoService.createGeography(geo);
		log.info("Created Geography "+geo.toString());
		return Response.status(Status.CREATED).entity(createdOrg).build();
	}
	
	@Path("/{Id}")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGeography(@Context SecurityContext sc,@PathParam("Id") Long GeographyId){
		log.info(" GeographyController.getGeography INVOKED  BY: " +sc.getUserPrincipal().getName());
		Geography foundOrg =  geoService.getGeographyById(GeographyId);
		if(foundOrg!=null){
			return Response.status(Status.OK).entity(foundOrg).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{Id}")
	@PUT
	@RolesAllowed({"IS","MARKETING"})
	public Response updateGeography(@Context SecurityContext sc,@PathParam("Id") Long GeographyId, @Valid Geography geo){
		log.info(" GeographyController.updateGeography INVOKED  BY: " +sc.getUserPrincipal().getName());
		try{
			geoService.updateGeography(GeographyId ,geo);
		}catch(DBException dbe){
			return Response.status(Status.NOT_FOUND).build();
		}
		return  Response.status(Status.NO_CONTENT).build();
	}
	
}
