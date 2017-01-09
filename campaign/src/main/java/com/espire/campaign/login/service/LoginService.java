package com.espire.campaign.login.service;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.login.dao.LoginDao;
import com.espire.domain.User;

@Stateless
public class LoginService {

	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	LoginDao loginDao;
	
	@PostConstruct
	public void init(){
		loginDao = new LoginDao(em);
	}
	
	public User getUser(String userPrincipal){
		//return loginDao.getUserByPrincipal(userPrincipal);
		
		return loginDao.getUserByPrincipaltyped(userPrincipal);
	}
	
	public User getUser(Long userId,String userPrinclpal){
		return null;
	}
	
}
