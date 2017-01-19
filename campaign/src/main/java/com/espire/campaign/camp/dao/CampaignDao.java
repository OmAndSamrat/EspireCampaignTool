package com.espire.campaign.camp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.espire.campaign.exception.DBException;
import com.espire.domain.Campaign;
import com.espire.domain.Status;

public class CampaignDao {

	EntityManager em;

	public CampaignDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public List<Campaign> listCamapaigns(Integer index , Integer count){
		TypedQuery<Campaign> query = em.createQuery("select camp from Campaign camp "
				+"where camp.softDelete = 1",Campaign.class); 
		if(index!=null && count!=null){
			query.setFirstResult(index);
			query.setMaxResults(count);
		}
		List <Campaign> resultList = query.getResultList();

		if(!resultList.isEmpty()){
			return resultList;
		}
		return null;
	}
	
	public Campaign createCampaign(Campaign camp) throws DBException{
		try{
			if(camp.getStatus()!=null){
				Status stat = em.createQuery("Select stat from Status stat where stat.statusDesc = :statusCode",Status.class)
						.setParameter("statusCode", camp.getStatus().getStatusDesc()).getSingleResult();
				camp.setStatus(stat);
			}
			em.persist(camp);
		}catch(Exception ex){
			camp=null;
			throw new DBException();
		}
		return camp;
	}
	
	public Campaign getCampaignById(Long campaignId){
		Campaign camp = em.find(Campaign.class, campaignId);
		return camp;
	}
	
	public void updateCampaign(Long campaignId , Campaign camp) throws DBException{
		Campaign dbCampaign = em.find(Campaign.class, campaignId);
		camp.setCampaignID(campaignId);
		if(dbCampaign==null){
			throw new DBException("Campaign not found for id "+campaignId);
		}
		Status stat = em.createQuery("Select stat from Status stat where stat.statusDesc = :statusCode",Status.class)
				.setParameter("statusCode", camp.getStatus().getStatusDesc()).getSingleResult();

		if(stat!=null){
			camp.setStatus(stat);
		}

		em.merge(camp);
	}
	
}
