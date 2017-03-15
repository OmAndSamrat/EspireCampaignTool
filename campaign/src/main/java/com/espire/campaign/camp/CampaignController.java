package com.espire.campaign.camp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.espire.campaign.camp.service.CampaignService;
import com.espire.campaign.exception.DBException;
import com.espire.campaign.security.EncryptUtil;
import com.espire.domain.Campaign;
import com.espire.domain.Communication;
import com.espire.domain.Edm;
import com.espire.domain.User;

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
		return  Response.status(Status.OK).entity(camp).build();
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
		log.info(" CampaignController.listCampaignCommunication INVOKED BY " +sc.getUserPrincipal().getName());
		
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
		log.info(" CampaignController.deleteCampaignCommunication INVOKED BY " +sc.getUserPrincipal().getName());
		
		try {
			Communication comm = new Communication();
			comm.setCommunicationID(communcationId);
			campaignService.deleteCommuncation(campaignId,comm);
		} catch (DBException e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		
		return Response.status(Status.NO_CONTENT).entity("Communication Deleted.").build();
	}
	
	@POST
	@Path("/{id}/edms/{edmid}/try")
	@RolesAllowed({"IS"})
	public Response tryCampaign(@Context SecurityContext sc,@PathParam("id") Long campaignId,@PathParam("edmid") Long edmId){
		log.info(" CampaignController.tryCampaign INVOKED BY " +sc.getUserPrincipal().getName());
		User loginUser = (User)sc.getUserPrincipal();
		try{
			campaignService.runCampaign(loginUser,campaignId, edmId,true);
		}catch(IllegalArgumentException ie){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(loginUser).build();
	}
	
	@POST
	@Path("/{id}/edms/{edmid}/execute")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"IS"})
	public Response executeCampaign(@Context SecurityContext sc,@PathParam("id") Long campaignId,@PathParam("edmid") Long edmId){
		log.info(" CampaignController.executeCampaign INVOKED BY " +sc.getUserPrincipal().getName());
		User loginUser = (User)sc.getUserPrincipal();
		try{
			campaignService.runCampaign(loginUser,campaignId, edmId,false);
		}catch(IllegalArgumentException ie){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(loginUser).build();
	}
	
	@PUT
	@Path("/{id}/edms/{edmid}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"MARKETING"})
	public Response uploadEdm(@Context SecurityContext sc,
			MultipartFormDataInput  multipart ,@PathParam("edmid") Long edmId){
		log.info(" CampaignController.uploadEdm INVOKED BY " +sc.getUserPrincipal().getName());
		
		Map<String, List<InputPart>> inputmap =  multipart.getFormDataMap();
		List<InputPart> inputPartList = inputmap.get("uploadFile");
		List<InputPart> subjectInputPartList = inputmap.get("subject");
		
		String fileData=null;
		String subjectData=null;
		try {
			for(InputPart ip : inputPartList){
				InputStream inputStream = ip.getBody(InputStream.class,null);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
				fileData =buffer.lines().collect(Collectors.joining("\n"));
			}
			for(InputPart ip : subjectInputPartList){
				InputStream inputStream = ip.getBody(InputStream.class,null);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
				subjectData =buffer.lines().collect(Collectors.joining("\n"));
			}
			
		} catch (IOException e) {
			log.error(e);
		}
		campaignService.updateEdmHtml(edmId, fileData, subjectData);
		return Response.status(Status.OK).entity(edmId).build();
	}
	
	@POST
	@Path("/{id}/edms")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"MARKETING"})
	public Response createEdm(@Context SecurityContext sc,
			MultipartFormDataInput  multipart ,@PathParam("id") Long campaignId){
		log.info(" CampaignController.uploadEdm INVOKED BY " +sc.getUserPrincipal().getName());
		
		Map<String, List<InputPart>> inputmap =  multipart.getFormDataMap();
		List<InputPart> inputPartList = inputmap.get("uploadFile");
		List<InputPart> subjectInputPartList = inputmap.get("subject");
		
		String fileData=null;
		String subjectData=null;
		try {
			for(InputPart ip : inputPartList){
				InputStream inputStream = ip.getBody(InputStream.class,null);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
				fileData =buffer.lines().collect(Collectors.joining("\n"));
			}
			for(InputPart ip : subjectInputPartList){
				InputStream inputStream = ip.getBody(InputStream.class,null);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
				subjectData =buffer.lines().collect(Collectors.joining("\n"));
			}
			
		} catch (IOException e) {
			log.error(e);
		}
		Edm edm = new Edm();
		Campaign camp = new Campaign();
		camp.setCampaignID(campaignId);
		edm.setCampaign(camp);
		edm.setSubject(subjectData);
		edm.setEdmHtml(fileData);
		edm = campaignService.createEdm(edm);
		return Response.status(Status.OK).entity(edm.getEdmId()).build();
	}
	
	/*@POST
	@Path("/{id}/edms")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"MARKETING"})
	public Response createEdm(@Context SecurityContext sc,
			@Valid Edm edm){
		log.info(" CampaignController.createEdm INVOKED BY " +sc.getUserPrincipal().getName());
		return Response.status(Status.OK).entity(campaignService.createEdm(edm)).build();
	}*/
	
	@POST
	@Path("/edmupdate")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"MARKETING"})
	public Response updateEdmSender(@Context SecurityContext sc, @Valid Edm edm){
		log.info(" CampaignController.updateEdm INVOKED BY " +sc.getUserPrincipal().getName());
		//encrypt EDM password.
		String password = "";
		Edm returnedEdm = null;
		try {
			returnedEdm = campaignService.getEdm(edm.getEdmId());
			password = EncryptUtil.encrypt(edm.getSenderPassword());
			returnedEdm.setSenderPassword(password);
			returnedEdm.setSenderEmail(edm.getSenderEmail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.OK).entity(campaignService.updateEdm(returnedEdm)).build();
	}
	
	@GET
	@Path("/{id}/edms")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"MARKETING"})
	public Response getEdms(@Context SecurityContext sc){
		log.info(" CampaignController.getEdms INVOKED BY " +sc.getUserPrincipal().getName());
		return Response.status(Status.OK).entity(campaignService.listEmds()).build();
	}
	
	@GET
	@Path("/{edmid}/prreport")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"MARKETING"})
	public Response getEdmEmailSendReport(@Context SecurityContext sc,@PathParam("edmid") Long edmId){
		log.info(" CampaignController.getEdms INVOKED BY " +sc.getUserPrincipal().getName());
		
		return Response.status(Status.OK).entity(campaignService.getProgressReport(edmId)).build();
	}
	
	@GET
	@Path("/edms/{edmid}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"MARKETING"})
	public Response getEdm(@Context SecurityContext sc, @PathParam("edmid") Long edmId) {
		log.info(" CampaignController.getEdms INVOKED BY " +sc.getUserPrincipal().getName());
		Edm edm = null;
		try {
			edm =campaignService.getEdm(edmId);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.OK).entity(edm).build();
	}
}
