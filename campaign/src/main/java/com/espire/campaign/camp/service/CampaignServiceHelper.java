package com.espire.campaign.camp.service;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.camp.dao.CampaignDao;
import com.espire.campaign.exception.DBException;
import com.espire.domain.CommunicationTracker;
import com.espire.domain.Edm;
import com.espire.domain.Status;

@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class CampaignServiceHelper {

	@PersistenceContext(unitName = "campaign-pu")
	private EntityManager em;

	private CampaignDao campaignDao;

	@PostConstruct
	public void init(){
		campaignDao = new CampaignDao(em);
	}

	public Edm getEdmGraphbyId(Long edmId)throws DBException{

		Edm edm = null;
		edm = campaignDao.getEdmGraphById(edmId);
		return edm;
	}
	
	public Status getStatusByDesc(String statusDesc){
		return campaignDao.getStatusByDesc(statusDesc);
	}
	
	public CommunicationTracker createCommTracker (CommunicationTracker ct){
		return campaignDao.createCommTracker(ct);
	}
	
	public Edm updateEdm(Edm  edm) {
		return campaignDao.updateEdm(edm);
	}
	
	public CommunicationTracker getCommTracker (Long ctId){
		return campaignDao. getCommTracker( ctId);
	}

}
