package com.espire.campaign.camp.service;

import java.util.List;

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
import com.espire.domain.Campaign;
import com.espire.domain.Communication;

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
}
