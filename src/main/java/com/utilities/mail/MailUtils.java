package com.utilities.mail;

import com.utilities.files.FileUtilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;

// TODO: Auto-generated Javadoc
/** The Class MailUtils. */
public class MailUtils {

  /**
   * Enable mail.
   *
   * @param toAddr the to addr
   * @param toBeEmailed the to be emailed
   * @param outDir the out dir
   * @param fileName the file name
   * @param fromAddr the from addr
   * @param srvrIpAddress the srvr ip address
   * @return true, if successful
   * @throws Exception the exception
   */
  public static boolean enableMail(
      String toAddr,
      String toBeEmailed,
      String outDir,
      String fileName,
      String fromAddr,
      String srvrIpAddress)
      throws Exception {
    boolean mailProcess = true;
    FileReader fr = null;
    BufferedReader br = null;

    try {
      if (toBeEmailed.equalsIgnoreCase("Y")) {
        String subject = null;
        String s = "";
        String body = "";
        String filePath = outDir + "/wip/" + fileName;
        fr = new FileReader(FileUtilities.validateFilePath(filePath));

        for (br = new BufferedReader(fr); (s = br.readLine()) != null; body = body + s) {}

        mailProcess =
            eMailSenderSMTPORSMTPS(fromAddr, toAddr, subject, body, filePath, srvrIpAddress);
      }
    } finally {

      try {
        if (fr != null) {
          fr.close();
        }
      } catch (IOException var31) {
        mailProcess = false;
      }

      try {
        if (br != null) {
          br.close();
        }
      } catch (IOException var30) {
        mailProcess = false;
      }
    }

    return mailProcess;
  }

  /**
   * E mail sender SMTPORSMTPS.
   *
   * @param from the from
   * @param to the to
   * @param mailSubject the mail subject
   * @param mailBody the mail body
   * @param filePath the file path
   * @param ipAddress the ip address
   * @return true, if successful
   * @throws Exception the exception
   */
  private static boolean eMailSenderSMTPORSMTPS(
      String from,
      String to,
      String mailSubject,
      String mailBody,
      String filePath,
      String ipAddress)
      throws Exception {
    boolean mailSend = true;
    InitialContext ic = null;
    Session session = null;
    ic = new InitialContext();
    String smtpHost = "";
    String smtpUser = "";
    String smtpPassword = "";
    session = (Session) ic.lookup("javax.mail.Session");
    Transport transport = session.getTransport();
    MimeMessage msg = new MimeMessage(session);
    msg.setFrom();
    InternetAddress[] address = new InternetAddress[] {new InternetAddress(to)};
    msg.setRecipients(RecipientType.TO, address);
    msg.setSubject(mailSubject);
    msg.setSentDate(new Date());
    MimeBodyPart mbp2 = new MimeBodyPart();
    FileDataSource fds = new FileDataSource(filePath);
    mbp2.setDataHandler(new DataHandler(fds));
    mbp2.setFileName(fds.getName());
    Multipart mp = new MimeMultipart();
    mp.addBodyPart(mbp2);
    msg.setContent(mp);
    msg.setSentDate(new Date());
    msg.setText(mailBody);
    transport.connect(smtpHost, smtpUser, smtpPassword);
    transport.sendMessage(msg, msg.getRecipients(RecipientType.TO));
    return mailSend;
  }
}
