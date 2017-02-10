package com.espire.email.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.espire.domain.Edm;
import com.espire.domain.User;
import com.espire.email.configuration.Configuration;
import com.espire.email.job.BatchEmailJob;
import com.espire.email.job.EmailJob;

public abstract class JobFactory {
	
	final static Logger log = Logger.getLogger(JobFactory.class);	
	private String emailTemplate ="";

	public abstract BatchEmailJob createEmailJobs(User loginUser,Edm edm,boolean trial);
	
	public JobFactory(){
	}
	
		
	void parseEmailBody(EmailJob emailJob){
		
		String readTemplate = new String(emailTemplate);
		Pattern namepattern = Pattern.compile("%name%");
		Matcher matcher = namepattern.matcher(readTemplate);
		readTemplate=matcher.replaceAll(emailJob.getName());
		Pattern idPattern = Pattern.compile("%uniqueid%");
		matcher = idPattern.matcher(readTemplate);
		readTemplate=matcher.replaceAll(emailJob.getTrackingId().toString());
		//Set Micro site url in all clickable url
		Pattern baseUrl = Pattern.compile("%baseUrl%");
		matcher = baseUrl.matcher(readTemplate);
		readTemplate=matcher.replaceAll(Configuration.getProperty("baseurl"));
		//Set communication tracker Id in all url
		Pattern coomTrackerId = Pattern.compile("%communicationtrackerId%");
		matcher = coomTrackerId.matcher(readTemplate);
		readTemplate=matcher.replaceAll(emailJob.getTrackingId().toString());
		System.out.println(readTemplate);
		emailJob.setEmailBody(readTemplate);
	}

	public String getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

}
