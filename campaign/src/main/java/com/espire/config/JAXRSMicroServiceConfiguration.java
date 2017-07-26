package com.espire.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.espire.campaign.microsite.MicrositeController;

@ApplicationPath("micros")
public class JAXRSMicroServiceConfiguration extends Application {
	public JAXRSMicroServiceConfiguration(){
		
	}
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(MicrositeController.class);
        //resources.add(AuthFilter.class);
        return resources;
    }
}
