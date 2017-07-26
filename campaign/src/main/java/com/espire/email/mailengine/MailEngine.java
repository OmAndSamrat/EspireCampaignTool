package com.espire.email.mailengine;

import com.espire.domain.EmailSenderInfo;
import com.espire.email.job.EmailJob;

public interface MailEngine {

	public void sendEmail(EmailJob emailJob, EmailSenderInfo emailSender);
	
}
