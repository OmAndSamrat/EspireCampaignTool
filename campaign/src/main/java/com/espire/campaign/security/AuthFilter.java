package com.espire.campaign.security;


import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.espire.campaign.login.service.LoginService;
import com.espire.domain.User;


@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
	/**
	 * Apply the filter : check input request, validate or not with user auth
	 * @param containerRequest The request from Tomcat server
	 */

	@EJB
	LoginService loginService;

	@EJB
	UserContainer usercontainer;

	@Context
	private HttpServletRequest request;
	
	final static Logger log = Logger.getLogger(ContainerRequestFilter.class);	

	@Override
	public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {

		User user;

		//Get the authentication passed in HTTP headers parameters
		String auth = containerRequest.getHeaderString("authorization");

		String token = containerRequest.getHeaderString("authKey");

		if(token!=null){
			user = usercontainer.getUser(token);
			if(user == null){
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}else{
			//If the user does not have the right (does not provide any HTTP Basic Auth)
			if(auth == null) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

			//lap : loginAndPassword
			String[] lap = BasicAuth.decode(auth);

			//If login or password fail
			if(lap == null || lap.length != 2) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			user = loginService.getUser(lap[0]);
			if (user!=null && lap[1].equals(user.getPwd())){
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(1200);
				usercontainer.addUser(user, session.getId());
				log.info("User authenticated "+user.getName()+" authKey Generated:"+session.getId());
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}

		String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
		containerRequest.setSecurityContext(new ApplicationSecurityContext(user, scheme));

	}
}