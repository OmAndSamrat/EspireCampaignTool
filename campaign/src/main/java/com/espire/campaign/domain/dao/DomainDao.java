package com.espire.campaign.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.espire.campaign.exception.DBException;
import com.espire.domain.Domain;

public class DomainDao {

	EntityManager em;

	public DomainDao(){}
	public DomainDao(EntityManager em){	
		this.em = em;
	}

	public List<Domain> listDomains(Integer index , Integer count){
		TypedQuery<Domain> query = em.createQuery("select dom from Domain dom "
				+"where dom.softDelete = 1",Domain.class); 
		if(index!=null && count!=null){
			query.setFirstResult(index);
			query.setMaxResults(count);
		}
		List <Domain> resultList = query.getResultList();

		if(!resultList.isEmpty()){
			return resultList;
		}
		return null;
	}

	public Domain createDomain(Domain dom){
		em.persist(dom);
		return dom;
	}
	
	public Domain getDomainById(Long domId){
		TypedQuery<Domain> query = em.createQuery("select dom from Domain dom "
				+"where dom.softDelete = 1 and dom.domainID = :domid",Domain.class); 
		
		query.setParameter("domid", domId);
		
		List <Domain> resultList = query.getResultList();

		if(!resultList.isEmpty()){
			return resultList.get(0);
		}
		return null;
	}
	
	public void updateDomain (Long domId ,Domain dom) throws DBException{
		
		Domain dbdom = em.find(Domain.class, domId);
		if(dbdom!=null){
			em.merge(dom);
		}
		else{
			throw new DBException(" Domain doesnot exist");
		}
	}

}
