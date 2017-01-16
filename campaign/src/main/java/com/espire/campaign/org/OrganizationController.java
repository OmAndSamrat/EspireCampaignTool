package com.espire.campaign.org;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.espire.campaign.org.service.OrganizationService;
import com.espire.domain.Organization;

@Path("/organizations")
public class OrganizationController {
	
	@EJB
	OrganizationService orgService;

	@GET
	@RolesAllowed({"IS","MARKETING"})
	public List<Organization> listOrganzations(@Context SecurityContext sc,
			@QueryParam("index")Integer resultIndex,@QueryParam("count") Integer resultCount){
		return orgService.listOrganzations(resultIndex, resultCount);
	}
	
	@POST
	@RolesAllowed({"IS","MARKETING"})
	public void createOrganization(@Context SecurityContext sc, @Context HttpServletResponse response,Organization org){
		
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
	
	@Path("/{Id}")
	@RolesAllowed({"IS","MARKETING"})
	public Organization getOrganization(@Context SecurityContext sc,@PathParam("Id") Long organizationId){
		return null;
	}
	
}
