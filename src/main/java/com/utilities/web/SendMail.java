package com.utilities.web;

import java.util.Date;
import java.util.Map;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SendMail {

    public void send(String userId, String pwd, String userEmails, String mailType, Map<?, ?> params) {
	String smtpHost = (String) params.get("SMTPS_HOST");
	String smtpUser = (String) params.get("SMTPS_USER");
	String smtpPwd = (String) params.get("SMTPS_PASSWORD");
	String smtpJndi = (String) params.get("SMTPS_JNDI");
	String bodyMessage = null;
	String subject = null;

	try {
	    InitialContext ic = new InitialContext();
	    Session session = (Session) ic.lookup(smtpJndi);
	    Transport transport = session.getTransport();
	    Message msg = new MimeMessage(session);
	    msg.setFrom();
	    msg.setSubject(subject);
	    msg.setSentDate(new Date());
	    MimeBodyPart mbp = new MimeBodyPart();
	    mbp.setText(bodyMessage);
	    Multipart mp = new MimeMultipart();
	    mp.addBodyPart(mbp);
	    msg.setContent(mp);
	    msg.addRecipient(RecipientType.TO, new InternetAddress(userEmails));
	    transport.connect(smtpHost, smtpUser, smtpPwd);
	    transport.sendMessage(msg, msg.getRecipients(RecipientType.TO));
	} catch (NamingException var28) {

	} catch (MessagingException var29) {

	}

    }
}
