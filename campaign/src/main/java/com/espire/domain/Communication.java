package com.espire.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Communication {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long communicationID;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="ContactID")
	private Contact contact;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="CampaignID")
	private Campaign campaign;
	
	@JsonIgnore
	private Integer softDelete =1 ;
	
	public Long getCommunicationID() {
		return communicationID;
	}
	public void setCommunicationID(Long communicationID) {
		this.communicationID = communicationID;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
}
