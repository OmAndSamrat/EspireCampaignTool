package com.espire.campaign.microsite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.espire.campaign.exception.DBException;
import com.espire.campaign.interaction.service.CaptureInteractionService;
import com.espire.domain.CaptureInteraction;
import com.espire.domain.EdmEvent;

@Path("/ms")
public class MicrositeController {
	
	final static Logger log = Logger.getLogger(MicrositeController.class);

	@EJB
	CaptureInteractionService captureInteractionService;
	/**
	 * This will be called against Anchor tag. 
	 * @param request /{communicationTrackerId}/{evenedmId}
	 * @return
	 */
	@GET
	@Path("/href/{communicationTrackerId}/{evenedmId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequestDetail(@Context HttpServletRequest request, 
			@PathParam("communicationTrackerId") Long communicationTrackerId, 
			@PathParam("evenedmId") Long eventEdmId) {
		String url = "";
		URI uri = null;
		try {
			url = createInteractionRecord(request, communicationTrackerId, eventEdmId);
			url = (url=="")?"google.com":url;
			uri = new URI(url);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return Response.seeOther(uri).build();
	}
	
	/**
	 * 
	 * @param request
	 * @param communicationTrackerId
	 * @param eventEdmId
	 * @return
	 */
	@Path("/img/{communicationTrackerId}/{evenedmId}")
	@GET
	@Produces({"image/png", "image/jpeg", "image/gif"})
	public Response  renderImage(@Context HttpServletRequest request, 
			@PathParam("communicationTrackerId") Long communicationTrackerId,
			@PathParam("evenedmId") Long eventEdmId) {
		
		//URI imageURI = null;
		String imageName = "";
		try {
			imageName = createInteractionRecord(request, communicationTrackerId, eventEdmId);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		String relativeWebPath = "/assets/images/campaignloader.png";
		InputStream inputStream = request.getServletContext().getResourceAsStream(relativeWebPath);
		byte[] imageData = null;
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();)
	    {
			/*imageData = Files.readAllBytes(Paths.get("D://campaignloader.png"));*/
	        byte[] buffer = new byte[0xFFFF];
	        for (int len; (len = inputStream.read(buffer)) != -1;) {
	            os.write(buffer, 0, len);
	        }
	        os.flush();
	        imageData = os.toByteArray();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new ByteArrayInputStream(imageData)).build();
		//return Response.status(Status.OK).entity("Image found").build();
	}
	private String createInteractionRecord(HttpServletRequest request, Long communicationTrackerId, Long eventEdmId) throws URISyntaxException {
		EdmEvent edmEvent = null; 
		if(communicationTrackerId !=null && eventEdmId!=null) {
			CaptureInteraction captureInteraction = MicroSiteUtil.createCommunicationTracker(request, communicationTrackerId, eventEdmId);
			try {
				edmEvent = captureInteractionService.createInteractionDetail(captureInteraction );
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return edmEvent!=null?edmEvent.getEvent().getHref():"";
	}

}
