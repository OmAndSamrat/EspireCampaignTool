package com.espire.campaign.login.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Root;

import com.espire.domain.Role;
import com.espire.domain.User;
import com.espire.domain.User_;


public class LoginDao {

	EntityManager em;
	
	public LoginDao(){}
	public LoginDao(EntityManager em){	
		this.em = em;
	}
	
	public User getUserByPrincipal(String userId){
		Query query = em.createQuery("select usr from User usr JOIN FETCH usr.userRoles where usr.userName = :userId "); 
		query.setParameter("userId", userId);
		List resultList = query.getResultList();
		
		if(!resultList.isEmpty()){
			return (User)resultList.get(0) ;
		}
		return null;
	}
	
	public User getUserByPrincipaltyped(String userId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> userRoot = cq.from(User.class);
		Fetch<User,Role> userroleJoin = userRoot.fetch(User_.userRoles);
		cq.select(userRoot);
		cq.where(cb.equal(userRoot.get(User_.userName), userId));
		TypedQuery<User> q = em.createQuery(cq);
		List<User> userList = q.getResultList();
		return userList.get(0);
	}

}
