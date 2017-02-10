package com.espire.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ValidateAuth
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {

	public JAXRSConfiguration(){
		
	}
	
}
