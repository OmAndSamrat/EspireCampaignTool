package com.espire.domain;

import java.io.Serializable;

public class EmailSenderInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5082031785869720029L;
	
	private String senderEmail;
	private String senderPassword;
	
	public EmailSenderInfo(String senderEmail, String senderPassword) {
		this.senderEmail = senderEmail;
		this.senderPassword = senderPassword;
	}
	
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getSenderPassword() {
		return senderPassword;
	}
	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}
}
