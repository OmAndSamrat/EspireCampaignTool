package com.espire.email.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.espire.domain.Edm;
import com.espire.domain.User;
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
		readTemplate=matcher.replaceAll(emailJob.getTrackingId());
		emailJob.setEmailBody(readTemplate);
	}

	public String getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

}
