package com.espire.campaign.contact.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.espire.campaign.exception.DBException;
import com.espire.domain.DesignationGroup;

public class DesignationGroupDao {

	EntityManager em;
	
	final static Logger log = Logger.getLogger(DesignationGroupDao.class);	
	
	public DesignationGroupDao(){}
	public DesignationGroupDao(EntityManager em){	
		this.em = em;
	}
	
	public List<DesignationGroup> listDesignationGroups(Integer index , Integer count){
		TypedQuery<DesignationGroup> query = em.createQuery("select dg from DesignationGroup dg "
				+"where dg.softDelete = 1",DesignationGroup.class); 
		if(index!=null && count!=null){
			query.setFirstResult(index);
			query.setMaxResults(count);
		}
		List <DesignationGroup> resultList = query.getResultList();
		//fetch designations for each Designationgroup using Proxy to avoid Cartesian product problem
		//https://learningviacode.blogspot.in/2012/08/fetch-join-and-cartesian-product-problem.html
		for(DesignationGroup dg :resultList){ 
			dg.getDesignationList().size();
		}

		if(!resultList.isEmpty()){
			return resultList;
		}
		return null;
	}

	public DesignationGroup createDesignationGroup(DesignationGroup dg){
		em.persist(dg);
		return dg;
	}
	
	public DesignationGroup getDesignationGroupById(Long dgId){
		TypedQuery<DesignationGroup> query = em.createQuery("select dg from DesignationGroup dg "
				+"where dg.softDelete = 1 and dg.designationGroupId = :dgid",DesignationGroup.class); 
		
		query.setParameter("dgid", dgId);
		
		List <DesignationGroup> resultList = query.getResultList();
		//fetch designationList  using Proxy to avoid Cartesian product problem
		if(!resultList.isEmpty()){
			resultList.get(0).getDesignationList().size();
			return resultList.get(0);
		}
		return null;
	}
	
	public void updateDesignationGroup (Long dgId ,DesignationGroup dg) throws DBException{
		
		DesignationGroup dbdg = em.find(DesignationGroup.class, dgId);
		if(dbdg!=null){
			em.merge(dg);
		}
		else{
			throw new DBException(" DesignationGroup doesnot exist");
		}
	}
}
