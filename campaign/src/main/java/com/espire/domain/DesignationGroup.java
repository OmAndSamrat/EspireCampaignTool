package com.espire.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="designationgroup")
public class DesignationGroup {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long designationGroupId;
	
	@Size(max=50)
	private String designationGroup;
	
	@JsonIgnore
	private Integer softDelete =1;
	
	@JsonManagedReference
	@OneToMany(mappedBy="designationGroup")
	private List<Designation> designationList;
	
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
	public List<Designation> getDesignationList() {
		return designationList;
	}
	public void setDesignationList(List<Designation> designationList) {
		this.designationList = designationList;
	}
	
}
