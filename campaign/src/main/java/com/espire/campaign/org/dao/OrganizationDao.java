package com.espire.campaign.org.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.espire.campaign.exception.DBException;
import com.espire.domain.Domain;
import com.espire.domain.Geography;
import com.espire.domain.Organization;

public class OrganizationDao {

	EntityManager em;

	public OrganizationDao(){}
	public OrganizationDao(EntityManager em){	
		this.em = em;
	}

	public List<Organization> listOrganzations(Integer index , Integer count){
		TypedQuery<Organization> query = em.createQuery("select org from Organization org "
				+"where org.softDelete = 1",Organization.class); 
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

	public Organization createOrganization(Organization org){
		
		if(org.getDomain()!=null && org.getDomain().getDomainID()!=null){
			org.setDomain(em.find(Domain.class, org.getDomain().getDomainID()));
		}
		if(org.getGeography()!=null && org.getGeography().getGeographyID()!=null){
			org.setGeography(em.find(Geography.class, org.getGeography().getGeographyID()));
		}
		
		em.persist(org);
		return org;
	}
	
	public Organization getOrganizationById(Long orgId){
		TypedQuery<Organization> query = em.createQuery("select org from Organization org "
				+"where org.softDelete = 1 and org.organisationID = :orgid",Organization.class); 
		
		query.setParameter("orgid", orgId);
		
		List <Organization> resultList = query.getResultList();

		if(!resultList.isEmpty()){
			return resultList.get(0);
		}
		return null;
	}
	
	public void updateOrganization (Long orgId ,Organization org) throws DBException{
		if(org.getDomain()!=null && org.getDomain().getDomainID()!=null){
			org.setDomain(em.find(Domain.class, org.getDomain().getDomainID()));
		}
		if(org.getGeography()!=null && org.getGeography().getGeographyID()!=null){
			org.setGeography(em.find(Geography.class, org.getGeography().getGeographyID()));
		}
		Organization dbOrg = em.find(Organization.class, orgId);
		if(dbOrg!=null){
			em.merge(org);
		}
		else{
			throw new DBException(" Organization doesnot exist");
		}
	}

}
