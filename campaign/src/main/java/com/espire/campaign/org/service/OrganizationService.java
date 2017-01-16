package com.espire.campaign.org.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.org.dao.OrganizationDao;
import com.espire.domain.Organization;

@Stateless
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
	
}
