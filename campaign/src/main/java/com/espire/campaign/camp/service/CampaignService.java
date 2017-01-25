package com.espire.campaign.camp.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.camp.dao.CampaignDao;
import com.espire.campaign.exception.DBException;
import com.espire.domain.Campaign;
import com.espire.domain.Communication;
import com.espire.domain.CommunicationTracker;
import com.espire.domain.Edm;
import com.espire.domain.Status;
import com.espire.domain.User;
import com.espire.email.job.BatchEmailJob;
import com.espire.email.jobexecutor.EmailJobExecutor;
import com.espire.email.jobexecutor.EmailJobExecutorImpl;
import com.espire.email.mailengine.SendEmailEngine;
import com.espire.email.main.EmailJobFactory;

@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class CampaignService {
	
	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	private CampaignDao campaignDao;
	
	@PostConstruct
	public void init(){
		campaignDao = new CampaignDao(em);
	}
	
	public List<Campaign> listCamapaigns(Integer index , Integer count){
		return campaignDao.listCamapaigns(index , count);
	}
	
	public Campaign createCampaign(Campaign camp) throws DBException {
		return campaignDao.createCampaign(camp);
	}
	
	public Campaign getCampaignById(Long campaignId){
		return campaignDao.getCampaignById(campaignId);
	}
	
	public void updateCampaign(Long campaignId , Campaign camp) throws DBException{
		campaignDao.updateCampaign(campaignId , camp);
	}
	
	public Communication createCommuncation (Long campaignId , Communication communication) throws DBException{
		return campaignDao.createCommuncation(campaignId , communication);
	}

	public List<Communication> listCommuncation (Long campaignId,Integer index , Integer count ) throws DBException{
		return campaignDao.listCommuncation(campaignId, index, count);
	}
	
	public void deleteCommuncation (Long campaignId , Communication communication ) throws DBException{
		campaignDao.deleteCommuncation(campaignId,communication);
	}
	
	@Asynchronous
	@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
	public void runCampaign(User loginUser,Long campaignId , Long edmId,Boolean trialMode){
		try {
			Edm edm = campaignDao.getEdm(edmId);
			BatchEmailJob batchJob =new EmailJobFactory(this).createEmailJobs(loginUser,edm,true);
			EmailJobExecutor executor = new EmailJobExecutorImpl(new SendEmailEngine(),this);
			executor.sendBulkEmail(batchJob);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	@TransactionAttribute(value=TransactionAttributeType.SUPPORTS)
	public Status getStatusByDesc(String statusDesc){
		return campaignDao.getStatusByDesc(statusDesc);
	}
	
	public CommunicationTracker createCommTracker (CommunicationTracker ct){
		return campaignDao.createCommTracker(ct);
	}
	
	public CommunicationTracker getCommTracker (Long ctId){
		return campaignDao. getCommTracker( ctId);
	}
}
