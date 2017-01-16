package com.espire.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Geography  implements Serializable {

	
	private static final long serialVersionUID = -5433126545598375331L;
	@Id
	@GeneratedValue
	private Long geographyID;
	private String geographyName;
	private Integer softDelete;
	
	
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
}
