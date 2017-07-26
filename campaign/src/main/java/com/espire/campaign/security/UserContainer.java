package com.espire.campaign.security;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.espire.domain.User;

@Startup
@Singleton
public class UserContainer {

	private Map<String , String> userTokenMap;
	private Map<String , User> tokenUserMap;
	
	@PostConstruct
	public void init(){
		userTokenMap = new ConcurrentHashMap<>();
		tokenUserMap = new ConcurrentHashMap<>();
	}
	@PreDestroy
	public void cleanup(){
		userTokenMap=null;
	}
	
	public String getUserToken(String userPrinciple){
		return (userPrinciple!=null)?userTokenMap.get(userPrinciple):null;
	}
	
	public void addUser(User user, String token){
		if(user!=null && token!=null){
			userTokenMap.put(user.getUserName(), token);
			tokenUserMap.put(token, user);
		}
	}
	
	public Set<String> getLoggedInUsers(){
		return userTokenMap.keySet();
	}
	
	public User getUser(String token){
		return (token!=null)?tokenUserMap.get(token):null;
	}
	
	public void removeUser(String userPrincipal){
		if(userPrincipal!=null){
			String token = userTokenMap.get(userPrincipal);
			userTokenMap.remove(userPrincipal);
			tokenUserMap.remove(token);
		}
	}
	
	public void removeUserByToken(String token){
		User user = tokenUserMap.get(token);
		if(user !=null){
			userTokenMap.remove(user.getUserName());
			tokenUserMap.remove(token);
		}
	}
}
