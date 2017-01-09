package com.espire.domain;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable ,Principal {

	
	private static final long serialVersionUID = 1320050810106402745L;
	@Id
	@GeneratedValue
	private Long userID;
	private String userName;
	private String pwd;
	private String firstName;
	private String lastName;
	
	@OneToMany
	@JoinTable(name="userrole", 
    joinColumns=@JoinColumn(name="UserID"),
    inverseJoinColumns=@JoinColumn(name="RoleID"))
	private List<Role> userRoles;
	public User(){};
	
	
	public User(Long userID, String userName, String pwd, String firstName, String lastName) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.pwd = pwd;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Role> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String getName() {
		return getUserName();
	}
}
