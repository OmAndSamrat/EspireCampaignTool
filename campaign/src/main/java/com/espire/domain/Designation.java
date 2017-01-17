package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Designation implements Serializable {

	private static final long serialVersionUID = -6804777639726815964L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long designationId;
	
	@Size(max=50)
	private String designation;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="DesignationGroupID")
	private DesignationGroup designationGroup;
	
	@JsonIgnore
	private Integer softDelete = 1;

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public DesignationGroup getDesignationGroup() {
		return designationGroup;
	}

	public void setDesignationGroup(DesignationGroup designationGroup) {
		this.designationGroup = designationGroup;
	}

	public Integer getSoftDelete() {
		return softDelete;
	}

	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
	
}
