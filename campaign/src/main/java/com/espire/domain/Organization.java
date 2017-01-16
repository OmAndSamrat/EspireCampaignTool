package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="organisation")
public class Organization  implements Serializable{

	
	private static final long serialVersionUID = 8110176111069725224L;
	@Id
	@GeneratedValue
	private Long organisationID;
	@ManyToOne
	private Domain domain;
	@ManyToOne
	private Geography geography;
	private String organisation;
	private String organisationRemark;
	private Integer softDelete;
	
	public Long getOrganisationID() {
		return organisationID;
	}
	public void setOrganisationID(Long organisationID) {
		this.organisationID = organisationID;
	}
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public Geography getGeography() {
		return geography;
	}
	public void setGeography(Geography geography) {
		this.geography = geography;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getOrganisationRemark() {
		return organisationRemark;
	}
	public void setOrganisationRemark(String organisationRemark) {
		this.organisationRemark = organisationRemark;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
}
