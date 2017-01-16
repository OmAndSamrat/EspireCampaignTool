package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Domain  implements Serializable {

	
	private static final long serialVersionUID = 814637703788775397L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long domainID;
	
	private String domainName;
	
	private String domainDescription;
	
	@JsonIgnore
	private Integer softDelete = 1;


	public Long getDomainID() {
		return domainID;
	}
	public void setDomainID(Long domainID) {
		this.domainID = domainID;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getDomainDescription() {
		return domainDescription;
	}
	public void setDomainDescription(String domainDescription) {
		this.domainDescription = domainDescription;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
	@Override
	public String toString() {
		return "Domain [domainID=" + domainID + ", domainName=" + domainName + ", domainDescription="
				+ domainDescription + ", softDelete=" + softDelete + "]";
	}
}
