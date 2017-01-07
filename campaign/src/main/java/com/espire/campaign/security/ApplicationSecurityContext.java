package com.espire.campaign.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.espire.domain.Role;
import com.espire.domain.User;

public class ApplicationSecurityContext implements SecurityContext {
	
	private User user;
	private String scheme;
	
	public ApplicationSecurityContext(User user, String scheme) {
	    this.user = user;
	    this.scheme = scheme;
	}

	@Override
	public Principal getUserPrincipal() {
		return user;
	}

	@Override
	public boolean isUserInRole(String role) {
		if (role !=null && user.getUserRoles() != null) {
			for (Role userRole : user.getUserRoles()){
				if(role.equals(userRole.getRoleName())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isSecure() {
		return "https".equals(this.scheme);
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}
	

}