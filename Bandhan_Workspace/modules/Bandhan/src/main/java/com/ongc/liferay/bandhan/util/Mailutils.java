package com.ongc.liferay.bandhan.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailutils {

	
	final static Log log = LogFactoryUtil.getLog(Mailutils.class.getName());

	
	public static boolean sendMail(String mailSubject, String mailBody,
			String Location, String Name) {
		try {
			if (Location == null) {
				Location = "";
			}
			if (Name == null) {
				Name = "";
			}
			StringBuffer mailBuffer = new StringBuffer();
			String MailBody = "<html>" + "<body>" + "<table>" + "<tr>"
					+ "<td>Dear Representative</td>" + "</tr><td></td><tr>"
					+ "</tr><td></td><tr>" + "<td><b>Name: </b></td>" + "<td>"
					+ Name
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><b>Date & Time: </b></td>"
					+ "<td>"
					+ new Date()
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><b>Settlement Location: </b></td>"
					+ "<td>"
					+ Location
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><b>Subject: </b></td>"
					+ "<td>"
					+ mailSubject
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><b>Message: </b></td>"
					+ "<td>"
					+ mailBody
					+ "</td>"
					+ "</tr>"
					+ "<tr></tr>"
					+ "<tr></tr>"
					+ "<tr>"
					+ "<td><b>Regards </b></td></tr>"
					+ "<tr><td>"
					+ Name
					+ "</td></tr>"
					+ "</tr>"
					+ "<tr><td></td></tr>"
					+ "<tr><td></td></tr>"
					+ "<tr><td></td></tr>"
					+ "<tr><td>This is system generated Mail</td></tr>"
					+ "</table>" + "</body>" + "</html>";
			mailBuffer.append(MailBody);

			String sToEmailID = PropertiesUtil.getProperty("MAIL_To");
			log.info("sToEmailID::::" + sToEmailID);

			String SMTP_HOST = PropertiesUtil.getProperty("SMTP_HOST");
			log.info("SMTP_HOST::::" + SMTP_HOST);

			final String username = PropertiesUtil.getProperty("Username");
			final String password = PropertiesUtil.getProperty("Password");

			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");

			Session mailersession = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			Message message = new MimeMessage(mailersession);

			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			String sFinalMsg = "";
			String[] to = { sToEmailID, "" };
			String MailFrom = PropertiesUtil.getProperty("Username");
			if (sToEmailID != null && sToEmailID.length() > 1) {
				message.setFrom(new javax.mail.internet.InternetAddress(
						MailFrom));

				message.addRecipient(javax.mail.Message.RecipientType.TO,
						new javax.mail.internet.InternetAddress(sToEmailID));
				// message.addRecipients(javax.mail.Message.RecipientType.TO,
				// arg1)
				message.setSubject(mailSubject);
				sFinalMsg = mailBuffer.toString();
				// system.out.println(sFinalMsg);
				htmlPart.setDataHandler(new DataHandler(sFinalMsg, "text/html"));
				multipart.addBodyPart(htmlPart);
				message.setContent(multipart);
				// String MailBody ="Dear Representative<br/><br/>Name: "+ Name
				// + "<br/>Date & Time: "+ new Date() +
				// "<br/>Settlement Location: "+ Location + "<br/>Subject: "+
				// mailSubject +
				// "<br/>Message:<br/>"+mailBody+"<br/><br/>Regards<br/>"+Name;
				// message.setContent(MailBody, "text/html");
				message.setSentDate(new Date());
				javax.mail.Transport.send(message);
				log.info("Mail successfully send.."+message);
			}
			return true;
		} catch (Exception ex) {
			log.info(ex);

		}
		return false;
	}

	public static boolean sendCovid19Mail(String mailSubject, String mailBody,String Location, String Name,String cpfno) {
		try {
			if (Location == null) {
				Location = "";
			}
			if (Name == null) {
				Name = "";
			}
			StringBuffer mailBuffer = new StringBuffer();
			String MailBody = "<html>" + "<body>" + "<table>" + "<tr>"
					+ "<td>Dear Representative</td>" + "</tr><td></td><tr>"
					+ "</tr><td></td>" +
					"<tr>"
					+ "<td><b>Cpf No: </b></td>"
					+ "<td>"
					+ cpfno
					+ "</td>"
					+ "</tr>"
					+"<tr>" + "<td><b>Name: </b></td>" + "<td>"
					+ Name
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><b>Date & Time: </b></td>"
					+ "<td>"
					+ new Date()
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><b>Settlement Location: </b></td>"
					+ "<td>"
					+ Location
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><b>Subject: </b></td>"
					+ "<td>"
					+ mailSubject
					+ "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><b>Message: </b></td>"
					+ "<td>"
					+ mailBody
					+ "</td>"
					+ "</tr>"
					+ "<tr></tr>"
					+ "<tr></tr>"
					+ "<tr>"
					+ "<td><b>Regards </b></td></tr>"
					+ "<tr><td>"
					+ Name
					+ "</td></tr>"
					+ "</tr>"
					+ "<tr><td></td></tr>"
					+ "<tr><td></td></tr>"
					+ "<tr><td></td></tr>"
					+ "<tr><td>This is system generated Mail</td></tr>"
					+ "</table>" + "</body>" + "</html>";
			mailBuffer.append(MailBody);

			String sToEmailID = PropertiesUtil.getProperty("MAIL_To");
			log.info("sToEmailID::::" + sToEmailID);

			String SMTP_HOST = PropertiesUtil.getProperty("SMTP_HOST");
			log.info("SMTP_HOST::::" + SMTP_HOST);

			final String username = PropertiesUtil.getProperty("Username");
			final String password = PropertiesUtil.getProperty("Password");

			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");

			Session mailersession = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			Message message = new MimeMessage(mailersession);

			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			String sFinalMsg = "";
			//String[] to = { sToEmailID, "awal_hk@ongc.co.in","mukherjee_d@ongc.co.in","s.singh@velocis.co.in" };
			String[] to = {"ret_help@ongc.co.in"};
				
			javax.mail.internet.InternetAddress[] toAddress = new javax.mail.internet.InternetAddress[to.length];
			 
            // To get the array of toaddresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new javax.mail.internet.InternetAddress(to[i]);
            }
			
			String MailFrom = PropertiesUtil.getProperty("Username");
			if (sToEmailID != null && sToEmailID.length() > 1) {
				message.setFrom(new javax.mail.internet.InternetAddress(
						MailFrom));

				//message.addRecipient(javax.mail.Message.RecipientType.TO,new javax.mail.internet.InternetAddress(sToEmailID));
				message.addRecipients(javax.mail.Message.RecipientType.TO, toAddress);
				message.setSubject(mailSubject);
				sFinalMsg = mailBuffer.toString();
				htmlPart.setDataHandler(new DataHandler(sFinalMsg, "text/html"));
				multipart.addBodyPart(htmlPart);
				message.setContent(multipart);
				message.setSentDate(new Date());
				javax.mail.Transport.send(message);
				log.info("Mail successfully send..");
			}
			return true;
		} catch (Exception ex) {
			log.info(ex);

		}
		return false;
	}

	
	public static boolean sendEnablerMail(String mailSubject, String mailBody,String enablerCpfno,String filePath) {
		log.info("sendEnablerMail::::start*******Mail**" );
		try {
			String sToEmailID=enablerCpfno+"@ongc.co.in";
			
			
			log.info("sToEmailID::::****" + sToEmailID);

			String SMTP_HOST = PropertiesUtil.getProperty("SMTP_HOST");
			log.info("SMTP_HOST::::" + SMTP_HOST);

			final String username = PropertiesUtil.getProperty("Username");
			final String password = PropertiesUtil.getProperty("Password");
			log.info("username::::" + username+" Password::::"+password);
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");

			Session mailersession = Session.getInstance(props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,password);
						}
					});

			Message message = new MimeMessage(mailersession);
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			MimeBodyPart attachmentPart = new MimeBodyPart();
			String[] to = {sToEmailID};//"s.singh@velocis.co.in","awal_hk@ongc.co.in","mukherjee_d@ongc.co.in"
			javax.mail.internet.InternetAddress[] toAddress = new javax.mail.internet.InternetAddress[to.length];
			 
            // To get the array of toaddresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new javax.mail.internet.InternetAddress(to[i]);
            }
			
			String MailFrom = PropertiesUtil.getProperty("Username");
			log.info("MailFrom::::*****" + MailFrom);
			if (sToEmailID != null && sToEmailID.length() > 1) {
				message.setFrom(new javax.mail.internet.InternetAddress(MailFrom));

				//message.addRecipient(javax.mail.Message.RecipientType.TO,new javax.mail.internet.InternetAddress(sToEmailID));
				message.addRecipients(javax.mail.Message.RecipientType.TO, toAddress);
				message.setSubject(mailSubject);
				htmlPart.setDataHandler(new DataHandler(mailBody, "text/html"));
				multipart.addBodyPart(htmlPart);
				if (filePath != null && filePath.length() > 1) {
					FileDataSource filesrc = new FileDataSource(filePath);
					attachmentPart.setDataHandler(new DataHandler(filesrc));
					attachmentPart.setFileName(filesrc.getName());
					multipart.addBodyPart(attachmentPart);
				}
				message.setContent(multipart);
				message.setSentDate(new Date());
				javax.mail.Transport.send(message);
				log.info("Mail successfully send..");
			}
			return true;
		} catch (Exception ex) {
			log.info(ex);

		}
		return false;
	}

	public static void main(String[] args) {
		sendMail("sss", "sss", "sss", "sss");
	}
}
