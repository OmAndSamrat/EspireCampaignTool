package com.espire.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DesignationGroup {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long designationGroupId;
	
	@Size(max=50)
	private String designationGroup;
	
	@JsonIgnore
	private Integer softDelete =1;
	
	public Long getDesignationGroupId() {
		return designationGroupId;
	}
	public void setDesignationGroupId(Long designationGroupId) {
		this.designationGroupId = designationGroupId;
	}
	public String getDesignationGroup() {
		return designationGroup;
	}
	public void setDesignationGroup(String designationGroup) {
		this.designationGroup = designationGroup;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
	
}
