package com.espire.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Campaign implements Serializable {

	private static final long serialVersionUID = 2651115373467893365L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long campaignID;

	@ManyToOne
	@JoinColumn(name="StatusID")
	private Status status;
	
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy" ,timezone="Asia/Calcutta")
	private Date campaignStartDate;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy" ,timezone="Asia/Calcutta")
	private Date campaignEndDate;
	private String campaignName;
	private String campaignDescription;
	
	@JsonIgnore
	private Integer softDelete = 1;
	
	public Long getCampaignID() {
		return campaignID;
	}
	public void setCampaignID(Long campaignID) {
		this.campaignID = campaignID;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getCampaignStartDate() {
		return campaignStartDate;
	}
	public void setCampaignStartDate(Date campaignStartDate) {
		this.campaignStartDate = campaignStartDate;
	}
	public Date getCampaignEndDate() {
		return campaignEndDate;
	}
	public void setCampaignEndDate(Date campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getCampaignDescription() {
		return campaignDescription;
	}
	public void setCampaignDescription(String campaignDescription) {
		this.campaignDescription = campaignDescription;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
}
