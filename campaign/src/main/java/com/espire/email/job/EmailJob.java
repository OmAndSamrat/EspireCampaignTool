package com.espire.email.job;

public class EmailJob {
	
	private String name;
	private Long trackingId;
	private String toAddress;
	private String ccAddress;
	private String bccAddress;
	private String emailBody;
	private String subject;
	
	public EmailJob() {
		super();
	}
	
	public EmailJob( String toAddress,String name) {
		super();
		this.name = name;
		this.toAddress = toAddress;
	}
	
	public EmailJob( Long trackingId, String toAddress,String name) {
		super();
		this.name = name;
		this.trackingId = trackingId;
		this.toAddress = toAddress;
	}
	public Long getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(Long trackingId) {
		this.trackingId = trackingId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
	public String getBccAddress() {
		return bccAddress;
	}
	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "EmailJob [name=" + name + ", trackingId=" + trackingId + ", toAddress=" + toAddress + "]";
	}
	
}
