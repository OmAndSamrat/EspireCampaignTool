package com.espire.campaign.org.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.exception.DBException;
import com.espire.campaign.org.dao.OrganizationDao;
import com.espire.domain.Organization;

@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class OrganizationService {

	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	OrganizationDao orgDao;
	
	@PostConstruct
	public void init(){
		orgDao = new OrganizationDao(em);
	}
	
	public List<Organization> listOrganzations(Integer index , Integer count){
		return orgDao.listOrganzations(index, count);
	}
	
	public Organization createOrganization(Organization org){
		return orgDao.createOrganization(org);
	}
	
	public Organization getOrganizationById(Long orgId){
		return orgDao.getOrganizationById(orgId);
	}
	
	public void updateOrganization(Long orgId , Organization org) throws DBException{
		orgDao.updateOrganization(orgId , org);
	}
	
}
