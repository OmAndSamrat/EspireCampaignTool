package com.espire.email.configuration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="emailconfiguration")
public class EmailConfiguration {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long configid;
	private String propertyname;
	private String propertyvalue;
	
	public Long getConfigid() {
		return configid;
	}
	public void setConfigid(Long configid) {
		this.configid = configid;
	}
	public String getPropertyname() {
		return propertyname;
	}
	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}
	public String getPropertyvalue() {
		return propertyvalue;
	}
	public void setPropertyvalue(String propertyvalue) {
		this.propertyvalue = propertyvalue;
	}
}
