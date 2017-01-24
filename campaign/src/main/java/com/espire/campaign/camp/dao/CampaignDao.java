package com.espire.campaign.camp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.espire.campaign.exception.DBException;
import com.espire.domain.Campaign;
import com.espire.domain.Communication;
import com.espire.domain.CommunicationTracker;
import com.espire.domain.Contact;
import com.espire.domain.Edm;
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
			Status stat = em.createQuery("select stat from Status stat where stat.statusDesc = :statusCode",Status.class)
					.setParameter("statusCode", "DRAFT").getSingleResult();
			camp.setStatus(stat);
			em.persist(camp);
			
			//Create list of trial communications
			List<Contact> trialContacts = em.createQuery("select cont from Contact cont where cont.trial=true",Contact.class).getResultList(); 
			for(Contact tc : trialContacts){
				Communication comm = new Communication();
				comm.setCampaign(camp);
				comm.setContact(tc);
				em.persist(comm);
			}
			
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
		Status stat = em.createQuery("select stat from Status stat where stat.statusDesc = :statusCode",Status.class)
				.setParameter("statusCode", camp.getStatus().getStatusDesc()).getSingleResult();

		if(stat!=null){
			camp.setStatus(stat);
		}

		em.merge(camp);
	}
	
	public Communication createCommuncation (Long campaignId , Communication communication) throws DBException{
		if(campaignId==null || communication.getContact() ==null || communication.getContact().getContactID()==null){
			throw new DBException("Campaign id can not be null");
		}
		Campaign dbCampaign = em.find(Campaign.class, campaignId);
		Contact dbContact = em.find(Contact.class, communication.getContact().getContactID());
		
		if(dbCampaign==null || dbContact ==null){
			throw new DBException("Campaign or Contact not found in the DB "+campaignId);
		}
		communication.setCampaign(dbCampaign);
		communication.setContact(dbContact);
		em.persist(communication);
		return communication;
	}
	
	public List<Communication> listCommuncation (Long campaignId,Integer index , Integer count ) throws DBException{
		if(campaignId==null ){
			throw new DBException("Campaign id cannot be null");
		}
		TypedQuery<Communication> communicationQuery = em.createQuery("select com from Communication com "
			+"where com.campaign.campaignID = :campaignId and com.softDelete=1",Communication.class);
		communicationQuery.setParameter("campaignId", campaignId);
		if(index!=null && count!=null){
			communicationQuery.setFirstResult(index);
			communicationQuery.setMaxResults(count);
		}
		return communicationQuery.getResultList();
	}
	
	public void deleteCommuncation (Long campaignId,Communication communication ) throws DBException{
		if(communication.getCommunicationID()==null ){
			throw new DBException("Communication id cannot be null");
		}
		
		Communication dbCommunication = em.find(Communication.class, communication.getCommunicationID());
		
		if(dbCommunication==null || dbCommunication.getCampaign().getCampaignID()!=campaignId){
			throw new DBException("Communication not found");
		}
		if("DRAFT"!=dbCommunication.getCampaign().getStatus().getStatusDesc()){
			throw new DBException("Campaign is not in draft status");
		}
		
		dbCommunication.setSoftDelete(0);
		em.persist(dbCommunication);
	}
	
	public Edm getEdm(Long edmId) throws DBException{
		if(edmId==null ){
			throw new DBException("edmId id cannot be null");
		}
		return em.find(Edm.class, edmId);
	}
	
	public Status getStatusByDesc(String statusDesc){
		Status stat = em.createQuery("select stat from Status stat where stat.statusDesc = :statusCode",Status.class)
				.setParameter("statusCode", statusDesc).getSingleResult();
		return stat;
	}
	
	public CommunicationTracker createCommTracker (CommunicationTracker ct){
		em.persist(ct);
		return ct;
	}
	
}
