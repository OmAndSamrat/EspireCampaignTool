package com.espire.campaign.domain.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.domain.dao.DomainDao;
import com.espire.campaign.exception.DBException;
import com.espire.domain.Domain;

@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class DomainService {

	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	private DomainDao domainDao;
	
	@PostConstruct
	public void init(){
		domainDao = new DomainDao(em);
	}
	
	public List<Domain> listDomains(Integer index , Integer count){
		return domainDao.listDomains(index, count);
	}
	
	public Domain createDomain(Domain dom){
		return domainDao.createDomain(dom);
	}
	
	public Domain getDomainById(Long domId){
		return domainDao.getDomainById(domId);
	}
	
	public void updateDomain(Long domId , Domain dom) throws DBException{
		domainDao.updateDomain(domId , dom);
	}
	
}
