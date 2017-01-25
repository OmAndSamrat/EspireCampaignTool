package com.espire.email.jobexecutor;

import org.apache.log4j.Logger;

import com.espire.campaign.camp.service.CampaignService;
import com.espire.domain.CommunicationTracker;
import com.espire.domain.Status;
import com.espire.email.configuration.Configuration;
import com.espire.email.job.BatchEmailJob;
import com.espire.email.job.EmailJob;
import com.espire.email.mailengine.MailEngine;

public class EmailJobExecutorImpl implements EmailJobExecutor {

	final static Logger log = Logger.getLogger(EmailJobExecutorImpl.class);	
	MailEngine engine ;
	private CampaignService campaignService;
	public EmailJobExecutorImpl(MailEngine engineType,CampaignService cs){
		engine =  engineType;
		campaignService = cs;
	}
	
	@Override
	public void sendBulkEmail(BatchEmailJob batchJob){
		long sleepDuration = Long.parseLong(Configuration.getProperty("email.send.interval"));
		log.info("Starting job for JobId "+batchJob.getBatchJobId());
		log.info("Count of emails in job "+batchJob.getEmailJobList().size());
		batchJob.getEmailJobList().stream().forEach((job)->{
			try{
				Thread.sleep(sleepDuration);
				doSend(job);
				CommunicationTracker currentCt = campaignService.getCommTracker(job.getTrackingId());
				CommunicationTracker newCt = new CommunicationTracker();
				Status status = null;
				status = campaignService.getStatusByDesc("SENT"); 
				ct.setCommunication(comm);
				ct.setEdm(edm);
				ct.setUser(loginUser);
				 /* COMMUNICATION TRACKER ID GENERATED AT THIS STEP WILL BE USED FOR CREATING TRACKING IDS*/
				campaignService.createCommTracker(ct);
			}catch(InterruptedException ie){
				log.error(ie);
			}
		});
	}

	private void doSend(EmailJob email){
		log.info("Sending email :" +email.toString());
		engine.sendEmail(email);
	}

}