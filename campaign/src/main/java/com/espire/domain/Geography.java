package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Geography  implements Serializable {

	
	private static final long serialVersionUID = -5433126545598375331L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long geographyID;
	@Size(max=100)
	private String geographyName;
	
	@JsonIgnore
	private Integer softDelete = 1;
	
	
	public Long getGeographyID() {
		return geographyID;
	}
	public void setGeographyID(Long geographyID) {
		this.geographyID = geographyID;
	}
	public String getGeographyName() {
		return geographyName;
	}
	public void setGeographyName(String geographyName) {
		this.geographyName = geographyName;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}
	@Override
	public String toString() {
		return "Geography [geographyID=" + geographyID + ", geographyName=" + geographyName + ", softDelete="
				+ softDelete + "]";
	}
}
