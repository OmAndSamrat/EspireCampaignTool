package com.espire.email.job;

import java.util.List;

public class BatchEmailJob {

	private String batchJobId;
	List<EmailJob> emailJobList;
	
	public String getBatchJobId() {
		return batchJobId;
	}
	public void setBatchJobId(String batchJobId) {
		this.batchJobId = batchJobId;
	}
	public List<EmailJob> getEmailJobList() {
		return emailJobList;
	}
	public void setEmailJobList(List<EmailJob> emailJobList) {
		this.emailJobList = emailJobList;
	}
	@Override
	public String toString() {
		return "BatchEmailJob [batchJobId=" + batchJobId + ", emailJobList=" + emailJobList + "]";
	}
	
}
