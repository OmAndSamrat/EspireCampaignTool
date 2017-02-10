package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="event")
public class Event implements Serializable{
	private static final long serialVersionUID = 1059807731041289815L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long eventID;
	private String eventName;
	private String eventDescription;
	private Integer softDelete = -1;
	private Long eventGroupID;
	private String href;
	
	public Long getEventID() {
		return eventID;
	}
	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
	public Long getEventGroupID() {
		return eventGroupID;
	}
	public void setEventGroupID(Long eventGroupID) {
		this.eventGroupID = eventGroupID;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	

}
