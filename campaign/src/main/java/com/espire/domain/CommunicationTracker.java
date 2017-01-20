package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class CommunicationTracker implements Serializable {

	
	private static final long serialVersionUID = 1893516918121706795L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long communicationTrackerID;
	
	@ManyToOne
	@JoinColumn(name ="StatusID")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name ="CommunicationID")
	private Communication communication;
	
	@ManyToOne
	@JoinColumn(name ="UserID")
	private User user;
	
	@Transient
	Edm edm;

	public Long getCommunicationTrackerID() {
		return communicationTrackerID;
	}

	public void setCommunicationTrackerID(Long communicationTrackerID) {
		this.communicationTrackerID = communicationTrackerID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Communication getCommunication() {
		return communication;
	}

	public void setCommunication(Communication communication) {
		this.communication = communication;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Edm getEdm() {
		return edm;
	}

	public void setEdm(Edm edm) {
		this.edm = edm;
	}
	
	
}