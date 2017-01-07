package com.espire.campaign.login.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.espire.domain.User;


public class LoginDao {

	EntityManager em;
	
	public LoginDao(){}
	public LoginDao(EntityManager em){	
		this.em = em;
	}
	
	public User getUserByPrincipal(String userId){
		Query query = em.createQuery("select usr from User usr where usr.userName = :userId"); 
		query.setParameter("userId", userId);
		List resultList = query.getResultList();
		
		if(!resultList.isEmpty()){
			return (User)resultList.get(0) ;
		}
		return null;
	}

}
