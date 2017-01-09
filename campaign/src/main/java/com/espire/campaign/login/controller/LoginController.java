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

import org.apache.log4j.Logger;

import com.espire.campaign.security.UserContainer;
import com.espire.domain.User;


@Path("/")
public class LoginController {
	
	@EJB
	UserContainer userContainer;
	
	final static Logger log = Logger.getLogger(LoginController.class);	

	@GET
	@Path("/test")
	@RolesAllowed("ADMIN")
	public String getMessage(@Context SecurityContext sc){		
		log.info("test message");		
		User user = (User)sc.getUserPrincipal();
		return "message "+ user.getFirstName();
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	@Path("/login")
	public User authenticateUser(@Context SecurityContext sc){
		User user =(User)sc.getUserPrincipal();
		log.info("User logged in  "+user.getName());		
		return user;
	}
	
	@Path("/logout")
	@POST
	public String logoutUser(@Context SecurityContext sc ,@Context HttpServletRequest httpServletRequest){
		HttpSession session = httpServletRequest.getSession();
		session.invalidate();
		User user =(User)sc.getUserPrincipal();
		log.info("User logged out  "+user.getName());	
		return "true";
	}
	
}
