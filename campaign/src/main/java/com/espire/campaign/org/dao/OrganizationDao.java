package com.espire.campaign.org.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.espire.domain.Organization;

public class OrganizationDao {

EntityManager em;
	
	public OrganizationDao(){}
	public OrganizationDao(EntityManager em){	
		this.em = em;
	}
	
	public List<Organization> listOrganzations(Integer index , Integer count){
		TypedQuery<Organization> query = em.createQuery("select org from Organization org where org.softDelete = 1",Organization.class); 
		if(index!=null && count!=null){
			query.setFirstResult(index);
			query.setMaxResults(count);
		}
		List <Organization> resultList = query.getResultList();
		
		if(!resultList.isEmpty()){
			return resultList;
		}
		return null;
	}
	
}
