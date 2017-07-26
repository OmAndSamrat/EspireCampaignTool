package com.espire.campaign.interaction.dao;

import javax.persistence.EntityManager;

import com.espire.campaign.exception.DBException;
import com.espire.domain.CaptureInteraction;
import com.espire.domain.CommunicationTracker;
import com.espire.domain.EdmEvent;
/**
 * 
 * @author om.pandey
 * The major responsibility of this class is to capture the interaction details.
 *
 */
public class CaptureInteractionDao {
	
	EntityManager em;
	
	public CaptureInteractionDao(EntityManager em) {
		this.em = em;
	}
	
	public EdmEvent createInteractionDetail(CaptureInteraction captureInteraction) throws DBException {
		Long communicationTrackerId  = captureInteraction.getCommunicationTrackerID();
		Long eventEdmId = captureInteraction.getEventEDMID();
		
		if(communicationTrackerId==null){
			throw new DBException("Communication tracker id can not be null");
		}
		if(eventEdmId==null){
			throw new DBException("EventEdm id can not be null");
		}
		

		
		CommunicationTracker dbCommunicationTracker = em.find(CommunicationTracker.class, communicationTrackerId);
		EdmEvent dbContact = em.find(EdmEvent.class, eventEdmId);
		
		if(dbCommunicationTracker==null || dbContact ==null){
			throw new DBException("Campaign or Contact not found in the DB "+communicationTrackerId);
		}
		//communication.setCampaign(dbCommunicationTracker);
		//communication.setContact(dbContact);
		em.persist(captureInteraction);
		return dbContact;
	}

}
