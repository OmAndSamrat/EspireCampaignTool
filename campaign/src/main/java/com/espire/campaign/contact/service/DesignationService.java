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

import com.espire.campaign.contact.dao.DesignationDao;
import com.espire.campaign.exception.DBException;
import com.espire.domain.Designation;


@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class DesignationService {

	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	DesignationDao designationDao;
	
	@PostConstruct
	public void init(){
		designationDao = new DesignationDao(em);
	}
	
	public List<Designation> listDesignations(Integer index , Integer count){
		return designationDao.listDesignations(index, count);
	}
	
	public Designation createDesignation(Designation dom){
		return designationDao.createDesignation(dom);
	}
	
	public Designation getDesignationById(Long domId){
		return designationDao.getDesignationById(domId);
	}
	
	public void updateDesignation(Long domId , Designation dom) throws DBException{
		designationDao.updateDesignation(domId , dom);
	}
}
