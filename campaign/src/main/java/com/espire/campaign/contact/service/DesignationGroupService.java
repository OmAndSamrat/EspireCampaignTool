package com.espire.campaign.contact.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.contact.dao.DesignationGroupDao;
import com.espire.campaign.exception.DBException;
import com.espire.domain.DesignationGroup;


@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class DesignationGroupService {

	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	DesignationGroupDao dgDao;
	
	@PostConstruct
	public void init(){
		dgDao = new DesignationGroupDao(em);
	}
	
	public List<DesignationGroup> listDesignationGroups(Integer index , Integer count){
		return dgDao.listDesignationGroups(index, count);
	}
	
	public DesignationGroup createDesignationGroup(DesignationGroup dom){
		return dgDao.createDesignationGroup(dom);
	}
	
	public DesignationGroup getDesignationGroupById(Long domId){
		return dgDao.getDesignationGroupById(domId);
	}
	
	public void updateDesignationGroup(Long domId , DesignationGroup dom) throws DBException{
		dgDao.updateDesignationGroup(domId , dom);
	}
}
