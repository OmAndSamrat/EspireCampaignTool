package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Status implements Serializable {

	
	private static final long serialVersionUID = 398786439749634684L;
	@Id
	private Long statusID;
	@Column(name="status")
	private String statusDesc;
	@JsonIgnore
	private Integer softDelete=1;
	
	public Status(){
		
	}
	
	public Status(Long statusID, String statusDesc) {
		super();
		this.statusID = statusID;
		this.statusDesc = statusDesc;
	}
	public Long getStatusID() {
		return statusID;
	}
	public void setStatusID(Long statusID) {
		this.statusID = statusID;
	}
	
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
