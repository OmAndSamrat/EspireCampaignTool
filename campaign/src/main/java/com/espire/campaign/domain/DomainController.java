package com.espire.campaign.domain;

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

import com.espire.campaign.domain.service.DomainService;
import com.espire.campaign.exception.DBException;
import com.espire.domain.Domain;

@Path("/domains")
public class DomainController {
	
	@EJB
	private DomainService domService;
	
	final static Logger log = Logger.getLogger(DomainController.class);	

	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response listDomains(@Context SecurityContext sc,
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount){
		log.info(" DomainController.listDomains INVOKED BY " +sc.getUserPrincipal().getName());
		log.info("caling listDomains index:"+resultIndex+" count:"+resultCount);
		
		if(resultIndex==null ^ resultCount==null){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(domService.listDomains(resultIndex, resultCount)).build();
	}
	
	@POST
	@RolesAllowed({"IS","MARKETING"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDomain(@Context SecurityContext sc, @Valid Domain dom){
		log.info(" DomainController.createDomain INVOKED BY " +sc.getUserPrincipal().getName());
		if(dom.getDomainID()!=null){
			return Response.status(Status.BAD_REQUEST).entity("{\"error\":\"ID cannot be sent while creating an entity\"}").build();
		}
		Domain created = domService.createDomain(dom);
		if(created ==null){
			Response.status(Status.BAD_REQUEST).build();
		}
		log.info("Created Domain "+dom.toString());
		return Response.status(Status.CREATED).entity(created).build();
	}
	
	@Path("/{Id}")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDomain(@Context SecurityContext sc,@PathParam("Id") Long DomainId){
		log.info(" DomainController.getDomain INVOKED BY " +sc.getUserPrincipal().getName());
		Domain found =  domService.getDomainById(DomainId);
		if(found!=null){
			return Response.status(Status.OK).entity(found).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{Id}")
	@PUT
	@RolesAllowed({"IS","MARKETING"})
	public Response updateDomain(@Context SecurityContext sc,@PathParam("Id") Long DomainId, @Valid Domain dom){
		log.info(" DomainController.updateDomain INVOKED BY " +sc.getUserPrincipal().getName());
		try{
			domService.updateDomain(DomainId ,dom);
		}catch(DBException dbe){
			return Response.status(Status.NOT_FOUND).build();
		}
		return  Response.status(Status.NO_CONTENT).build();
	}
	
}
