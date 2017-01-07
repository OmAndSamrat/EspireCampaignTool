package com.espire.campaign.login.controller;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.espire.campaign.security.UserContainer;
import com.espire.domain.User;


@Path("/")
public class LoginController {
	
	@EJB
	UserContainer userContainer;

	@GET
	@Path("/test")
	@RolesAllowed("ADMIN")
	public String getMessage(@Context SecurityContext sc){		
				
		User user = (User)sc.getUserPrincipal();
		return "message "+ user.getFirstName();
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	@Path("/login")
	public User authenticateUser(@Context SecurityContext sc){
		return (User)sc.getUserPrincipal();
	}
	
	@Path("/logout")
	@POST
	public String logoutUser(@Context SecurityContext sc ,@Context HttpServletRequest httpServletRequest){
		HttpSession session = httpServletRequest.getSession();
		session.invalidate();
		return "true";
	}
	
}
