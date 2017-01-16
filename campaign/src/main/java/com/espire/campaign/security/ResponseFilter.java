package com.espire.campaign.security;

import java.io.IOException;

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
			responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:8080/campaign/*");
			String userPrinciple = requestContext.getSecurityContext().getUserPrincipal()!=null?requestContext.getSecurityContext().getUserPrincipal().getName():null;
			if(usercontainer.getUserToken(userPrinciple)!=null){
				responseContext.getHeaders().add("authKey", usercontainer.getUserToken(userPrinciple));
			}
			
		
	}

}
