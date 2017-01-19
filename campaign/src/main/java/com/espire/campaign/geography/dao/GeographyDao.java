package com.espire.campaign.geography.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.espire.campaign.exception.DBException;
import com.espire.domain.Geography;

public class GeographyDao {

	EntityManager em;

	public GeographyDao(){}
	public GeographyDao(EntityManager em){	
		this.em = em;
	}

	public List<Geography> listGeographies(Integer index , Integer count){
		TypedQuery<Geography> query = em.createQuery("select geo from Geography geo "
				+"where geo.softDelete = 1",Geography.class); 
		if(index!=null && count!=null){
			query.setFirstResult(index);
			query.setMaxResults(count);
		}
		List <Geography> resultList = query.getResultList();

		if(!resultList.isEmpty()){
			return resultList;
		}
		return null;
	}

	public Geography createGeography(Geography geo){
		em.persist(geo);
		return geo;
	}
	
	public Geography getGeographyById(Long geoId){
		TypedQuery<Geography> query = em.createQuery("select geo from Geography geo "
				+"where geo.softDelete = 1 and geo.geographyID = :geoid",Geography.class); 
		
		query.setParameter("geoid", geoId);
		
		List <Geography> resultList = query.getResultList();

		if(!resultList.isEmpty()){
			return resultList.get(0);
		}
		return null;
	}
	
	public void updateGeography (Long geoId ,Geography geo) throws DBException{
		
		Geography dbgeo = em.find(Geography.class, geoId);
		if(dbgeo!=null){
			em.merge(geo);
		}
		else{
			throw new DBException(" Geography doesnot exist for id "+geoId);
		}
	}

}
