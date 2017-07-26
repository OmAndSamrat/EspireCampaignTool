package com.espire.email.mailengine;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.espire.campaign.security.EncryptUtil;
import com.espire.domain.EmailSenderInfo;
import com.espire.email.configuration.Configuration;
import com.espire.email.job.EmailJob;

public class SendEmailEngine implements MailEngine {

	Session session;
	final static Logger log = Logger.getLogger(SendEmailEngine.class);
	
	public void  sendEmail(EmailJob emailJob, EmailSenderInfo emailSender){
		
		String from = emailSender.getSenderEmail();
		String password = EncryptUtil.decrypt(emailSender.getSenderPassword());
		 
		 try {
			 session = Session.getInstance(Configuration.getProperties(),
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									from,
									EncryptUtil.decrypt(emailSender.getSenderPassword()));
						}
					  });

				Message message = new MimeMessage(session);
				message.addHeader("Content-type", "text/HTML; charset=UTF-8");
				message.addHeader("format", "flowed");
				message.addHeader("Content-Transfer-Encoding", "8bit");
					
				message.setFrom(new InternetAddress(from));
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(from));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailJob.getToAddress()));
				message.setSubject(emailJob.getSubject());
				message.setContent(emailJob.getEmailBody(), "text/HTML; charset=UTF-8");
				message.setSentDate(new Date());
				Transport.send(message,from,password);
			} catch (MessagingException e) {
				log.error(e);
			}
	}
	
}
