package com.espire.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext(); 
        servletContext.setInitParameter("resteasy.role.based.security", "true");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP.
    }

}
