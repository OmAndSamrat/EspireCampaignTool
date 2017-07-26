package com.espire.campaign.interaction.service;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.exception.DBException;
import com.espire.campaign.interaction.dao.CaptureInteractionDao;
import com.espire.domain.CaptureInteraction;
import com.espire.domain.EdmEvent;

@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)

public class CaptureInteractionService {
	
	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	CaptureInteractionDao captureInteractionDao;
	
	@PostConstruct
	public void init(){
		captureInteractionDao = new CaptureInteractionDao(em);
	}

	public EdmEvent createInteractionDetail(CaptureInteraction captureInteraction) throws DBException {
		return captureInteractionDao.createInteractionDetail(captureInteraction);
	}
}
