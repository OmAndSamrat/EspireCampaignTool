package com.espire.email.jobexecutor;

import com.espire.email.job.BatchEmailJob;

public interface EmailJobExecutor {

	public void sendBulkEmail(BatchEmailJob batchJob);
	
}
