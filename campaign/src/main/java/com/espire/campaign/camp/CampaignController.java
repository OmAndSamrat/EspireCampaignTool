package com.espire.campaign.camp;

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

import com.espire.campaign.camp.service.CampaignService;
import com.espire.campaign.exception.DBException;
import com.espire.domain.Campaign;
import com.espire.domain.Campaign;

@Path("/campaigns")
public class CampaignController {

	final static Logger log = Logger.getLogger(CampaignController.class);
	
	@EJB
	CampaignService campaignService;
	
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response listCampaigns(@Context SecurityContext sc,
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount){
		log.info(" CampaignController.listCampaigns INVOKED BY " +sc.getUserPrincipal().getName());
		log.info("caling listCampaigns index:"+resultIndex+" count:"+resultCount);
		
		if(resultIndex==null ^ resultCount==null){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(campaignService.listCamapaigns(resultIndex, resultCount)).build();
	}
	
	@POST
	@RolesAllowed({"IS","MARKETING"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCampaign(@Context SecurityContext sc, @Valid Campaign camp){
		log.info(" CampaignController.createCampaign INVOKED BY " +sc.getUserPrincipal().getName());
		
		if(camp.getCampaignID()!=null){
			return Response.status(Status.BAD_REQUEST).entity("{\"error\":\"ID cannot be sent while creating an entity\"}").build();
		}
		Campaign created = null;
		try{
			created = campaignService.createCampaign(camp);
		}catch(DBException dbe){
			return Response.status(Status.BAD_REQUEST).entity(dbe.getMessage()).build();
		}
		log.info("Created Campaign "+camp.toString());
		
		return Response.status(Status.CREATED).entity(created).build();
	}
	
	@Path("/{Id}")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCampaign(@Context SecurityContext sc,@PathParam("Id") Long campaignId){
		log.info(" CampaignController.getCampaign INVOKED BY " +sc.getUserPrincipal().getName());
		Campaign found =  campaignService.getCampaignById(campaignId);
		if(found!=null){
			return Response.status(Status.OK).entity(found).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{Id}")
	@PUT
	@RolesAllowed({"IS","MARKETING"})
	public Response updateCampaign(@Context SecurityContext sc,@PathParam("Id") Long campaignId, @Valid Campaign camp){
		log.info("CampaignController.updateCampaign INVOKED BY" +sc.getUserPrincipal().getName());
		try{
			campaignService.updateCampaign(campaignId ,camp);
		}catch(DBException dbe){
			return Response.status(Status.NOT_FOUND).build();
		}
		return  Response.status(Status.NO_CONTENT).build();
	}
}
