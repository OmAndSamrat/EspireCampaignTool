package com.espire.email.configuration;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class EmailConfigService {

	
	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	public List<EmailConfiguration> readEmailProperties(){
		return em.createQuery("select conf from EmailConfiguration conf", EmailConfiguration.class).getResultList();
	}
	
}
