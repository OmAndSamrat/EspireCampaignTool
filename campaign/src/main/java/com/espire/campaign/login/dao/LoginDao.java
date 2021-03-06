package com.espire.campaign.login.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.espire.domain.User;


public class LoginDao {

	EntityManager em;
	
	public LoginDao(){}
	public LoginDao(EntityManager em){	
		this.em = em;
	}
	
	public User getUserByPrincipal(String userId){
		User dbUser = null;
		try {
			TypedQuery<User> query = em.createQuery("select usr from User usr where usr.userName = :userId",User.class); 
			query.setParameter("userId", userId);
			dbUser= query.getSingleResult();
		} catch (NoResultException  noResultException) {
			dbUser = null;
		}
		return dbUser;
	}

}
