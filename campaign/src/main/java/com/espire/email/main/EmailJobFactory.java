package com.espire.email.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.espire.campaign.camp.service.CampaignService;
import com.espire.domain.Communication;
import com.espire.domain.CommunicationTracker;
import com.espire.domain.Edm;
import com.espire.domain.Status;
import com.espire.domain.User;
import com.espire.email.job.BatchEmailJob;
import com.espire.email.job.EmailJob;

public class EmailJobFactory extends JobFactory {

	final static Logger log = Logger.getLogger(EmailJobFactory.class);	

	private CampaignService campaignService;

	public EmailJobFactory( CampaignService campaignService){

		this.campaignService = campaignService;
	}

	public BatchEmailJob createEmailJobs(User loginUser,Edm edm,boolean trial){
		BatchEmailJob batchJob = new BatchEmailJob();
		batchJob.setBatchJobId(""+new Date().getTime());
		batchJob.setEmailJobList(new ArrayList<EmailJob>());

		for(Communication comm : edm.getCampaign().getCommunicationList()){

			if(comm.isTrial().equals(trial)){
				CommunicationTracker ct = new CommunicationTracker();
				Status status = null;
				if(trial){
					status = campaignService.getStatusByDesc("TESTED");
				}else{
					status = campaignService.getStatusByDesc("CREATED");
				}
				ct.setStatus(status);
				ct.setCommunication(comm);
				ct.setEdm(edm);
				ct.setUser(loginUser);
				campaignService.createCommTracker(ct);
				setEmailTemplate(edm.getEdmHtml());
				EmailJob emailJob= new EmailJob(ct.getCommunicationTrackerID().toString(),comm.getContact().getEmail(),comm.getContact().getContactName());
				emailJob.setSubject(edm.getSubject());
				parseEmailBody(emailJob);
				batchJob.getEmailJobList().add(emailJob);
				
			}

		}
		return batchJob;
	}

}
