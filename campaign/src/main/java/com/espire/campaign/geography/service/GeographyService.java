package com.espire.campaign.geography.service;

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
import com.espire.campaign.geography.dao.GeographyDao;
import com.espire.domain.Geography;

@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class GeographyService {

	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	GeographyDao geoainDao;
	
	@PostConstruct
	public void init(){
		geoainDao = new GeographyDao(em);
	}
	
	public List<Geography> listGeographies(Integer index , Integer count){
		return geoainDao.listGeographies(index, count);
	}
	
	public Geography createGeography(Geography geo){
		return geoainDao.createGeography(geo);
	}
	
	public Geography getGeographyById(Long geoId){
		return geoainDao.getGeographyById(geoId);
	}
	
	public void updateGeography(Long geoId , Geography geo) throws DBException{
		geoainDao.updateGeography(geoId , geo);
	}
	
}
