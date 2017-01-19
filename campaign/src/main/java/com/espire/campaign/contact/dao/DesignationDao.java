package com.espire.campaign.contact.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.espire.campaign.exception.DBException;
import com.espire.domain.Designation;

public class DesignationDao {

	EntityManager em;
	
	public DesignationDao(){}
	public DesignationDao(EntityManager em){	
		this.em = em;
	}
	
	public List<Designation> listDesignations(Integer index , Integer count){
		TypedQuery<Designation> query = em.createQuery("select designation from Designation designation "
				+"where designation.softDelete = 1",Designation.class); 
		if(index!=null && count!=null){
			query.setFirstResult(index);
			query.setMaxResults(count);
		}
		List <Designation> resultList = query.getResultList();

		if(!resultList.isEmpty()){
			return resultList;
		}
		return null;
	}

	public Designation createDesignation(Designation designation){
		em.persist(designation);
		return designation;
	}
	
	public Designation getDesignationById(Long designationId){
		TypedQuery<Designation> query = em.createQuery("select designation from Designation designation "
				+"where designation.softDelete = 1 and designation.designationId = :designationid",Designation.class); 
		
		query.setParameter("designationid", designationId);
		
		List <Designation> resultList = query.getResultList();

		if(!resultList.isEmpty()){
			return resultList.get(0);
		}
		return null;
	}
	
	public void updateDesignation (Long designationId ,Designation designation) throws DBException{
		
		Designation dbdesignation = em.find(Designation.class, designationId);
		if(dbdesignation!=null){
			em.merge(designation);
		}
		else{
			throw new DBException(" Designation doesnot exist for DesignationID "+designationId);
		}
	}
}
