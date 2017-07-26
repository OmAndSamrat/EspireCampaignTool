package com.espire.config;

import javax.ejb.EJB;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.espire.campaign.security.UserContainer;

@WebListener
public class ApplicationSessionListner implements HttpSessionListener {
	
	@EJB
	UserContainer container;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// nothing required here 

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		String token = se.getSession().getId();
		container.removeUserByToken(token);

	}

}
