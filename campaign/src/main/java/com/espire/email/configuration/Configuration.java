package com.espire.email.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;

@Singleton
@Startup
public class Configuration {
	
	
	@EJB
	EmailConfigService configService;
	private static Properties properties;
	final static Logger log = Logger.getLogger(Configuration.class);
	
	/**
	 * This method reads the emailConfiguration table.
	 * @param propertyFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@PostConstruct
	private void loadProperties() {
		
		List<EmailConfiguration> dbproperties =configService.readEmailProperties();
		if(dbproperties!=null && !dbproperties.isEmpty()){
			properties = new Properties();
			for(EmailConfiguration conf :dbproperties ){
				properties.put(conf.getPropertyname(), conf.getPropertyvalue());
			}
		}
	}
	
	public static String getProperty(String propertyName){
		return properties.getProperty(propertyName);
	}
	
	public static Properties getProperties(){
		return properties;
	}
	
	

}
