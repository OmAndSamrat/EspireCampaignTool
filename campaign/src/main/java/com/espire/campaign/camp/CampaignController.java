package com.espire.campaign.camp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.espire.domain.Communication;

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
	
	@Path("/{id}")
	@GET
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCampaign(@Context SecurityContext sc,@PathParam("id") Long campaignId){
		log.info(" CampaignController.getCampaign INVOKED BY " +sc.getUserPrincipal().getName());
		Campaign found =  campaignService.getCampaignById(campaignId);
		if(found!=null){
			return Response.status(Status.OK).entity(found).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{id}")
	@PUT
	@RolesAllowed({"IS","MARKETING"})
	public Response updateCampaign(@Context SecurityContext sc,@PathParam("id") Long campaignId, @Valid Campaign camp){
		log.info("CampaignController.updateCampaign INVOKED BY" +sc.getUserPrincipal().getName());
		try{
			campaignService.updateCampaign(campaignId ,camp);
		}catch(DBException dbe){
			return Response.status(Status.NOT_FOUND).build();
		}
		return  Response.status(Status.NO_CONTENT).build();
	}

	@POST
	@Path("/{id}/communications")
	@RolesAllowed({"IS","MARKETING"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response bulkCreateCampaignCommunication(@Context SecurityContext sc,@PathParam("id") Long campaignId, @Valid Set<Communication> communicationList){
		log.info(" CampaignController.bulkCreateCampaignCommunication INVOKED BY " +sc.getUserPrincipal().getName());
		
		if(communicationList.isEmpty()){
			return Response.status(Status.BAD_REQUEST).entity("{\"error\":\"Empty communicationlist\"}").build();
		}
		Set<Communication> returnList = new HashSet<>() ;
		for(Communication communication: communicationList){
			if(communication.getCommunicationID()!=null){
				return Response.status(Status.BAD_REQUEST).entity("{\"error\":\"ID cannot be sent while creating an entity\"}").build();
			}
			Communication created = null;
			try{
				created = campaignService.createCommuncation(campaignId,communication);
				returnList.add(created);
			}catch(DBException dbe){
				return Response.status(Status.BAD_REQUEST).build();
			}
			log.info("Created communication "+created.toString());
			
		}
		
		return Response.status(Status.CREATED).entity(returnList).build();
	}
	
	@GET
	@Path("/{id}/communications")
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response listCampaignCommunication(@Context SecurityContext sc,@PathParam("id") Long campaignId, 
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount){
		log.info(" CampaignController.createCampaign INVOKED BY " +sc.getUserPrincipal().getName());
		
		List<Communication> communicationList;
		try {
			communicationList = campaignService.listCommuncation(campaignId, resultIndex, resultCount);
		} catch (DBException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		return Response.status(Status.OK).entity(communicationList).build();
	}
	
	@DELETE
	@Path("/{id}/communications/{comId}")
	@RolesAllowed({"IS","MARKETING"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCampaignCommunication(@Context SecurityContext sc,@PathParam("id") Long campaignId, 
			@PathParam("comId") Long communcationId){
		log.info(" CampaignController.createCampaign INVOKED BY " +sc.getUserPrincipal().getName());
		
		try {
			Communication comm = new Communication();
			comm.setCommunicationID(communcationId);
			campaignService.deleteCommuncation(campaignId,comm);
		} catch (DBException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		return Response.status(Status.NO_CONTENT).build();
	}
	
}
