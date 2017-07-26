package com.espire.campaign.security;

import java.io.IOException;
import java.security.Principal;

import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseFilter implements ContainerResponseFilter{

	@EJB
	UserContainer usercontainer;

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		Principal principal = requestContext.getSecurityContext().getUserPrincipal();
		if(principal!=null){
			String userPrincipal = principal.getName();
			if(usercontainer.getUserToken(userPrincipal)!=null){
				responseContext.getHeaders().add("authKey", usercontainer.getUserToken(userPrincipal));
			}
		}
	}

}
