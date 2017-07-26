package com.espire.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="captureinteraction")
public class CaptureInteraction implements Serializable{
	
	private static final long serialVersionUID = 8110176111069725224L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long interactionID;
	private Long eventEDMID;
	private Long communicationTrackerID;
	private Double latitude;
	private Double longitude;
	private String iPAddress;
	private String device;
	@Column(insertable=false)
	private Date dateTimeInteraction;
	public Long getInteractionID() {
		return interactionID;
	}
	public void setInteractionID(Long interactionID) {
		this.interactionID = interactionID;
	}
	public Long getEventEDMID() {
		return eventEDMID;
	}
	public void setEventEDMID(Long eventEDMID) {
		this.eventEDMID = eventEDMID;
	}
	public Long getCommunicationTrackerID() {
		return communicationTrackerID;
	}
	public void setCommunicationTrackerID(Long communicationTrackerID) {
		this.communicationTrackerID = communicationTrackerID;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getiPAddress() {
		return iPAddress;
	}
	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Date getDateTimeInteraction() {
		return dateTimeInteraction;
	}
	public void setDateTimeInteraction(Date dateTimeInteraction) {
		this.dateTimeInteraction = dateTimeInteraction;
	}
	
	}
