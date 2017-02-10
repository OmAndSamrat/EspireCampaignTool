package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "edmevent")
public class EdmEvent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7144037603314824248L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long eventEDMID;
	private Long edmID;
	@ManyToOne
	@JoinColumn(name="eventID")
	private Event event;
	private Long interactionID;
	
	public Long getEventEDMID() {
		return eventEDMID;
	}
	public void setEventEDMID(Long eventEDMID) {
		this.eventEDMID = eventEDMID;
	}
	public Long getEdmID() {
		return edmID;
	}
	public void setEdmID(Long edmID) {
		this.edmID = edmID;
	}
	
	public Long getInteractionID() {
		return interactionID;
	}
	public void setInteractionID(Long interactionID) {
		this.interactionID = interactionID;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
	
}
