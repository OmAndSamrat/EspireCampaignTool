package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="contact")
public class Contact implements Serializable{
	private static final long serialVersionUID = -4892206315984362915L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long contactID;
	
	@Size(max=50)
	private String contactName;
	
	@Size(max=30)
	@Column(name="contactFirstName")
	private String firstName;
	
	@Size(max=30)
	@Column(name="contactSurName")
	private String surName;
	
	@Size(max=1)
	@Column(name="contactGender")
	private String gender;
	
	@NotNull
	@Pattern(regexp="\\S+@\\S+\\.\\S+")
	@Column(name="contactEmail")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="DesignationID")
	private Designation designation;
	
	@ManyToOne
	@JoinColumn(name="OrganisationID")
	private Organization organization;
	
	@Size(max=20)
	private String primaryPhone;
	
	@Size(max=20)	
	private String secondaryPhone;
	
	private Boolean trial=Boolean.FALSE;
	
	@JsonIgnore
	private Integer softDelete = 1;
	
	public Long getContactID() {
		return contactID;
	}
	public void setContactID(Long contactID) {
		this.contactID = contactID;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Designation getDesignation() {
		return designation;
	}
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
	public Boolean getTrial() {
		return trial;
	}
	public void setTrial(Boolean trial) {
		this.trial = trial;
	}
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	public String getSecondaryPhone() {
		return secondaryPhone;
	}
	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}
	
}
