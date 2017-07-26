package com.espire.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="edm")
public class Edm implements Serializable{

	private static final long serialVersionUID = 4864710301077709828L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long edmId;
	
	@JsonIgnore
	private String edmHtml;
	
	@ManyToOne
	@JoinColumn(name="CampaignID")
	private Campaign campaign;
	
	private String subject;
	@JsonIgnore
	private Integer softDelete = 1;
	
	@JsonIgnore
	@OneToMany(mappedBy="edm")
	private Set<CommunicationTracker> communications;
	
	@ManyToOne
	@JoinColumn(name ="StatusID")
	private Status status;
	
	private String senderEmail;
	private String senderPassword;
	
	
	public Long getEdmId() {
		return edmId;
	}
	public void setEdmId(Long edmId) {
		this.edmId = edmId;
	}
	public String getEdmHtml() {
		return edmHtml;
	}
	public void setEdmHtml(String edmHtml) {
		this.edmHtml = edmHtml;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Set<CommunicationTracker> getCommunications() {
		return communications;
	}
	public void setCommunications(Set<CommunicationTracker> communications) {
		this.communications = communications;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getSenderPassword() {
		return senderPassword;
	}
	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}
	
}
