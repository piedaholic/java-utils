package com.utilities.jms;

import com.utilities.string.StringUtils;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

// TODO: Auto-generated Javadoc
/** The Class QueueUtils. */
public class QueueUtils {

  /**
   * Write to Q.
   *
   * @param outChar the out char
   * @param message the message
   * @param queue the queue
   * @param factory the factory
   * @param refno the refno
   * @param callerId the caller id
   * @param callerPswd the caller pswd
   * @return true, if successful
   */
  public static boolean writeToQ(
      String outChar,
      String message,
      Queue queue,
      QueueConnectionFactory factory,
      String refno,
      String callerId,
      String callerPswd) {
    QueueSession session = null;
    QueueSender sender = null;
    QueueConnection qConnection = null;
    boolean qTransaction = false;
    int acknowledge = 1;
    boolean msgWritten = true;

    try {

      try {
        if (callerId != null) {
          if (callerPswd != null) {
            qConnection = factory.createQueueConnection(callerId, callerPswd);
          } else {
            return false;
          }
        } else {
          qConnection = factory.createQueueConnection();
        }

        session = qConnection.createQueueSession(qTransaction, acknowledge);
      } catch (JMSSecurityException var29) {
        // Authentication failed
        msgWritten = false;
      } catch (JMSException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      try {
        sender = session.createSender(queue);
        sender.setDeliveryMode(2);
      } catch (JMSException var28) {
        msgWritten = false;
      }

      if (sender != null) {
        try {
          TextMessage txtmessage = session.createTextMessage(message);
          txtmessage.setJMSCorrelationID(refno);
          sender.send(txtmessage);
          msgWritten = true;
          qConnection.close();
        } catch (JMSException var27) {
          msgWritten = false;
        }
      }
    } finally {
      try {
        if (null != sender) {
          sender.close();
        }

        if (null != session) {
          session.close();
        }

        if (null != qConnection) {
          qConnection.close();
        }
      } catch (Exception var26) {
      }
    }

    return msgWritten;
  }

  /**
   * Sets the swift message.
   *
   * @param message the message
   * @param swiftFormat the swift format
   * @param outChar the out char
   * @return the string
   */
  public static String setSwiftMessage(String message, String swiftFormat, String outChar) {
    try {
      if (message != null) {
        message = message.replaceAll("}\\n{", "}{");
        // replaceStr(message, "}" + String.valueOf('\n') + "{", "}{");
        message = message.replaceAll("-}\\n", "-}");
        // replaceStr(message, "-}" + String.valueOf('\n'), "-}");
        message = message.replaceAll("\\n", "\\r\\n");
        // replaceStr(message, String.valueOf('\n'), '\r' + String.valueOf('\n'));
        swiftFormat = swiftFormat != null && swiftFormat != "" ? swiftFormat : "1";
        if (swiftFormat.equals("1")) {
          message = message + outChar + '\r' + '\n';
        }

        if (swiftFormat.equals("2")) {
          message = message + outChar;
        }

        if (swiftFormat.equals("3")) {
          int k = Math.round(message.length() / 512.0F);
          int toPad = 511 + k * 512 - message.length();
          if (toPad >= 512) {
            toPad -= 512;
          }

          while (toPad > 64) {
            message = message + StringUtils.setString(63, ' ') + '\n';
            toPad -= 64;
          }
          message = message + StringUtils.setString(toPad, ' ');
        }
      } else {
        message = "There is no message to display";
      }

      return message;
    } catch (Exception var7) {
      return message;
    }
  }

  /**
   * Gets the msg from Q.
   *
   * @param queueConnectionFactory the queue connection factory
   * @param queue the queue
   * @param backUpQ the back up Q
   * @param callerId the caller id
   * @param callerPswd the caller pswd
   * @return the msg from Q
   */
  public static synchronized String[] getMsgFromQ(
      QueueConnectionFactory queueConnectionFactory,
      Queue queue,
      Queue backUpQ,
      String callerId,
      String callerPswd) {
    String[] ret = new String[] {null, null};

    try {
      QueueConnection queueConnection = null;
      QueueSession queueSession = null;
      QueueReceiver queueReceiver = null;
      QueueSender queueSender = null;
      TextMessage message = null;
      BytesMessage byteMessage = null;
      String corrId = null;
      int rNo;

      SecureRandom r = null;
      try {
        r = SecureRandom.getInstance("SHA-512");
      } catch (Exception e) {

      }

      try {
        if (callerId != null) {
          queueConnection = queueConnectionFactory.createQueueConnection(callerId, callerPswd);
        } else {
          queueConnection = queueConnectionFactory.createQueueConnection();
        }

        queueSession = queueConnection.createQueueSession(false, 1);
        queueReceiver = queueSession.createReceiver(queue);
        queueSender = queueSession.createSender(backUpQ);
        queueSender.setDeliveryMode(2);
        queueConnection.start();
        Message m = queueReceiver.receive(1L);
        if (m != null) {
          if (m instanceof TextMessage) {
            message = (TextMessage) m;
            ret[0] = message.getText();
            corrId = message.getJMSCorrelationID();
            if ((corrId == null || corrId.equals("null") || corrId.equals("")) && r != null) {
              rNo = r.nextInt();
              rNo = rNo > 0 ? rNo : -1 * rNo;
              corrId = String.valueOf(rNo);
            }

            ret[1] = corrId;
          } else if (m instanceof BytesMessage) {
            byteMessage = (BytesMessage) m;
            corrId = byteMessage.getJMSCorrelationID();
            if ((corrId == null || corrId.equals("null") || corrId.equals("")) && r != null) {
              rNo = r.nextInt();
              rNo = rNo > 0 ? rNo : -1 * rNo;
              corrId = String.valueOf(rNo);
            }

            ret[1] = corrId;
            byte[] l_read = new byte[1000];
            ByteArrayOutputStream l_bos = new ByteArrayOutputStream();
            int l_len;
            while ((l_len = byteMessage.readBytes(l_read, 1000)) != -1) {
              l_bos.write(l_read, 0, l_len);
            }

            ret[0] = l_bos.toString();
          }

          TextMessage txtmessage = queueSession.createTextMessage(ret[0]);
          txtmessage.setJMSCorrelationID(corrId);
          queueSender.send(txtmessage);
        }

        queueConnection.close();
      } catch (JMSException var28) {
      } finally {
        if (queueConnection != null) {
          try {
            queueConnection.close();
          } catch (JMSException var27) {
          }
        }
      }
    } catch (Exception var30) {
    }

    return ret;
  }

  /**
   * Delete msg frm Q.
   *
   * @param queueConnectionFactory the queue connection factory
   * @param backUpQ the back up Q
   * @param corrId the corr id
   * @param callerId the caller id
   * @param callerPswd the caller pswd
   */
  public static void deleteMsgFrmQ(
      QueueConnectionFactory queueConnectionFactory,
      Queue backUpQ,
      String corrId,
      String callerId,
      String callerPswd) {
    QueueConnection queueConnection = null;
    QueueSession queueSession = null;
    QueueReceiver queueReceiver = null;
    try {
      if (callerId != null) {
        queueConnection = queueConnectionFactory.createQueueConnection(callerId, callerPswd);
      } else {
        queueConnection = queueConnectionFactory.createQueueConnection();
      }

      queueSession = queueConnection.createQueueSession(false, 1);
      queueReceiver = queueSession.createReceiver(backUpQ, "JMSCorrelationID = '" + corrId + "'");
      queueConnection.start();
      Message m = queueReceiver.receive(1L);
      if (m != null && m instanceof TextMessage) {}

      queueConnection.close();
    } catch (JMSException var19) {
    } finally {
      if (queueConnection != null) {
        try {
          queueConnection.close();
        } catch (JMSException var18) {
        }
      }
    }
  }

  /**
   * Write file to Q.
   *
   * @param message the message
   * @param queue the queue
   * @param factory the factory
   * @param refno the refno
   * @param callerId the caller id
   * @param callerPswd the caller pswd
   * @return true, if successful
   */
  public static boolean writeFileToQ(
      String message,
      Queue queue,
      QueueConnectionFactory factory,
      String refno,
      String callerId,
      String callerPswd) {
    QueueSession session = null;
    QueueSender sender = null;
    QueueConnection qConnection = null;
    boolean qTransaction = false;
    int acknowledge = 1;
    boolean msgWritten = true;

    try {
      try {
        if (callerId != null) {
          qConnection = factory.createQueueConnection(callerId, callerPswd);
        } else {
          qConnection = factory.createQueueConnection();
        }

        session = qConnection.createQueueSession(qTransaction, acknowledge);
      } catch (Exception var26) {
        msgWritten = false;
      }

      try {
        sender = session.createSender(queue);
        sender.setDeliveryMode(2);
      } catch (Exception var25) {
        msgWritten = false;
      }

      if (sender != null) {
        try {
          TextMessage txtmessage = session.createTextMessage(message);
          txtmessage.setJMSCorrelationID(refno);
          sender.send(txtmessage);
          msgWritten = true;
          qConnection.close();
        } catch (Exception var24) {
          msgWritten = false;
        }
      }
    } finally {
      try {
        if (null != sender) {
          sender.close();
        }

        if (null != session) {
          session.close();
        }

        if (null != qConnection) {
          qConnection.close();
        }
      } catch (Exception var23) {
      }
    }

    return msgWritten;
  }

  /**
   * Send msg to Q.
   *
   * @param messageObj the message obj
   * @param p_correlationID the p correlation ID
   * @param p_JMS_QUEUE_ICF the p JM S QUEU E ICF
   * @param p_JMS_QUEUE_PROVIDER_URL the p JM S QUEU E PROVIDE R URL
   * @param p_JMS_QUEUE_SECURITY_PRINCIPAL the p JM S QUEU E SECURIT Y PRINCIPAL
   * @param p_JMS_QUEUE_SECURITY_CREDENTIALS the p JM S QUEU E SECURIT Y CREDENTIALS
   * @param p_JMS_QCF the p JM S QCF
   * @param p_JMS_QUEUE_NAME the p JM S QUEU E NAME
   * @param p_JMS_Q_ACKNOWLEDGE the p JM S Q ACKNOWLEDGE
   * @param p_JMS_Q_TRANSACTION the p JM S Q TRANSACTION
   * @param p_JMS_Q_DELIVERY_OPT the p JM S Q DELIVER Y OPT
   * @param p_JMS_Q_TIME_TO_LIVE the p JM S Q TIM E T O LIVE
   * @param p_JMS_Q_PRIORITY the p JM S Q PRIORITY
   * @param callerID the caller ID
   * @param callerPswd the caller pswd
   * @throws Exception the exception
   */
  public void sendMsgToQ(
      Object messageObj,
      String p_correlationID,
      String p_JMS_QUEUE_ICF,
      String p_JMS_QUEUE_PROVIDER_URL,
      String p_JMS_QUEUE_SECURITY_PRINCIPAL,
      String p_JMS_QUEUE_SECURITY_CREDENTIALS,
      String p_JMS_QCF,
      String p_JMS_QUEUE_NAME,
      String p_JMS_Q_ACKNOWLEDGE,
      String p_JMS_Q_TRANSACTION,
      int p_JMS_Q_DELIVERY_OPT,
      int p_JMS_Q_TIME_TO_LIVE,
      int p_JMS_Q_PRIORITY,
      String callerID,
      String callerPswd)
      throws Exception {
    String strMessage = null;
    boolean isStringMsg = false;
    Message jmsMessage = null;
    if (messageObj instanceof String) {
      strMessage = (String) messageObj;
      isStringMsg = true;
    } else if (messageObj instanceof TextMessage) {
      jmsMessage = (TextMessage) messageObj;
      strMessage = ((TextMessage) messageObj).getText();
    } else {
      if (!(messageObj instanceof Message)) {
        throw new Exception("Unknown JMS Message Type");
      }

      jmsMessage = (Message) messageObj;
      strMessage = "<<NonTextMessage>>";
    }

    boolean l_transacted = false;
    if ("true".equalsIgnoreCase(p_JMS_Q_TRANSACTION)) {
      l_transacted = true;
    }

    QueueSender l_queueSender = null;

    try {
      InitialContext l_jndiContext = null;
      if (p_JMS_QUEUE_ICF != null && p_JMS_QUEUE_PROVIDER_URL != null) {
        Properties l_jndi_props = new Properties();
        l_jndi_props.put("java.naming.factory.initial", p_JMS_QUEUE_ICF);
        l_jndi_props.put("java.naming.provider.url", p_JMS_QUEUE_PROVIDER_URL);
        if (p_JMS_QUEUE_SECURITY_PRINCIPAL != null) {
          if (p_JMS_QUEUE_SECURITY_CREDENTIALS == null) {
            throw new Exception(
                "p_JMS_QUEUE_SECURITY_CREDENTIALS should also be provided if p_JMS_QUEUE_SECURITY_PRINCIPAL is provided.");
          }

          l_jndi_props.put("java.naming.security.principal", p_JMS_QUEUE_SECURITY_PRINCIPAL);
          l_jndi_props.put("java.naming.security.credentials", p_JMS_QUEUE_SECURITY_CREDENTIALS);
        }

        l_jndiContext = new InitialContext(l_jndi_props);
      } else {
        l_jndiContext = new InitialContext();
      }
      QueueConnectionFactory l_QCF = (QueueConnectionFactory) l_jndiContext.lookup(p_JMS_QCF);
      if (p_JMS_QUEUE_NAME.indexOf("queue://") != -1) {
        p_JMS_QUEUE_NAME = p_JMS_QUEUE_NAME.substring(p_JMS_QUEUE_NAME.lastIndexOf("/") + 1);
      }

      Queue l_queue = (Queue) l_jndiContext.lookup(p_JMS_QUEUE_NAME);
      QueueConnection qc = null;
      if (qc == null) {
        if (callerID != null) {
          qc = l_QCF.createQueueConnection(callerID, callerPswd);
        } else {
          qc = l_QCF.createQueueConnection();
        }
      }

      int l_acknowledge = 1;
      if ("CLIENT_ACKNOWLEDGE".equalsIgnoreCase(p_JMS_Q_ACKNOWLEDGE)) {
        l_acknowledge = 2;
      } else if ("DUPS_OK_ACKNOWLEDGE".equalsIgnoreCase(p_JMS_Q_ACKNOWLEDGE)) {
        l_acknowledge = 3;
      }

      QueueSession qs = qc.createQueueSession(l_transacted, l_acknowledge);
      l_queueSender = qs.createSender(l_queue);
      if (isStringMsg) {
        TextMessage l_textMessage = qs.createTextMessage();
        l_textMessage.setText(strMessage);
        l_textMessage.setJMSCorrelationID(p_correlationID);
        jmsMessage = l_textMessage;
      }

      l_queueSender.send(
          (Message) jmsMessage,
          p_JMS_Q_DELIVERY_OPT,
          p_JMS_Q_PRIORITY,
          (long) p_JMS_Q_TIME_TO_LIVE);
      if (l_transacted) {
        qs.commit();
      }
    } finally {
      try {
        if (l_queueSender != null) {
          l_queueSender.close();
        }
      } catch (Exception var32) {
      }
    }
  }

  /**
   * Send msgs to Q.
   *
   * @param p_Msgs the p msgs
   * @param p_correlationID the p correlation ID
   * @param p_JMS_QUEUE_ICF the p JM S QUEU E ICF
   * @param p_JMS_QUEUE_PROVIDER_URL the p JM S QUEU E PROVIDE R URL
   * @param p_JMS_QUEUE_SECURITY_PRINCIPAL the p JM S QUEU E SECURIT Y PRINCIPAL
   * @param p_JMS_QUEUE_SECURITY_CREDENTIALS the p JM S QUEU E SECURIT Y CREDENTIALS
   * @param p_JMS_QCF the p JM S QCF
   * @param p_JMS_Q_ACKNOWLEDGE the p JM S Q ACKNOWLEDGE
   * @param p_JMS_Q_TRANSACTION the p JM S Q TRANSACTION
   * @param p_JMS_Q_DELIVERY_OPT the p JM S Q DELIVER Y OPT
   * @param p_JMS_Q_TIME_TO_LIVE the p JM S Q TIM E T O LIVE
   * @param p_JMS_Q_PRIORITY the p JM S Q PRIORITY
   * @param callerId the caller id
   * @param callerPswd the caller pswd
   * @throws Exception the exception
   */
  public void sendMsgsToQ(
      Map<?, ?> p_Msgs,
      String p_correlationID,
      String p_JMS_QUEUE_ICF,
      String p_JMS_QUEUE_PROVIDER_URL,
      String p_JMS_QUEUE_SECURITY_PRINCIPAL,
      String p_JMS_QUEUE_SECURITY_CREDENTIALS,
      String p_JMS_QCF,
      String p_JMS_Q_ACKNOWLEDGE,
      String p_JMS_Q_TRANSACTION,
      int p_JMS_Q_DELIVERY_OPT,
      int p_JMS_Q_TIME_TO_LIVE,
      int p_JMS_Q_PRIORITY,
      String callerId,
      String callerPswd)
      throws Exception {
    boolean l_transacted = false;
    if ("true".equalsIgnoreCase(p_JMS_Q_TRANSACTION)) {
      l_transacted = true;
    }

    Set<?> l_msgs = p_Msgs.keySet();
    Iterator<?> iterator = l_msgs.iterator();
    String p_strMsg = "";
    String p_JMS_QUEUE_NAME = "";
    QueueSender l_queueSender = null;

    try {
      InitialContext l_jndiContext = null;
      if (p_JMS_QUEUE_ICF != null && p_JMS_QUEUE_PROVIDER_URL != null) {
        Properties l_jndi_props = new Properties();
        l_jndi_props.put("java.naming.factory.initial", p_JMS_QUEUE_ICF);
        l_jndi_props.put("java.naming.provider.url", p_JMS_QUEUE_PROVIDER_URL);
        if (p_JMS_QUEUE_SECURITY_PRINCIPAL != null) {
          if (p_JMS_QUEUE_SECURITY_CREDENTIALS == null) {
            throw new Exception(
                "p_JMS_QUEUE_SECURITY_CREDENTIALS should also be provided if p_JMS_QUEUE_SECURITY_PRINCIPAL is provided.");
          }

          l_jndi_props.put("java.naming.security.principal", p_JMS_QUEUE_SECURITY_PRINCIPAL);
          l_jndi_props.put("java.naming.security.credentials", p_JMS_QUEUE_SECURITY_CREDENTIALS);
        }

        l_jndiContext = new InitialContext(l_jndi_props);
      } else {
        l_jndiContext = new InitialContext();
      }

      QueueConnectionFactory l_QCF = (QueueConnectionFactory) l_jndiContext.lookup(p_JMS_QCF);
      QueueConnection qc;
      if (callerId != null) {
        qc = l_QCF.createQueueConnection(callerId, callerPswd);
      } else {
        qc = l_QCF.createQueueConnection();
      }

      int l_acknowledge = 1;
      if ("CLIENT_ACKNOWLEDGE".equalsIgnoreCase(p_JMS_Q_ACKNOWLEDGE)) {
        l_acknowledge = 2;
      } else if ("DUPS_OK_ACKNOWLEDGE".equalsIgnoreCase(p_JMS_Q_ACKNOWLEDGE)) {
        l_acknowledge = 3;
      }

      QueueSession qs = qc.createQueueSession(l_transacted, l_acknowledge);

      while (iterator.hasNext()) {
        p_strMsg = (String) iterator.next();
        p_JMS_QUEUE_NAME = (String) p_Msgs.get(p_strMsg);
        if (p_JMS_QUEUE_NAME.indexOf("queue://") != -1) {
          p_JMS_QUEUE_NAME = p_JMS_QUEUE_NAME.substring(p_JMS_QUEUE_NAME.lastIndexOf("/") + 1);
        }

        Queue l_queue = (Queue) l_jndiContext.lookup(p_JMS_QUEUE_NAME);
        l_queueSender = qs.createSender(l_queue);
        TextMessage l_textMessage = qs.createTextMessage();
        l_textMessage.setText(p_strMsg);
        l_textMessage.setJMSCorrelationID(p_correlationID);
        l_queueSender.send(
            l_textMessage, p_JMS_Q_DELIVERY_OPT, p_JMS_Q_PRIORITY, (long) p_JMS_Q_TIME_TO_LIVE);

        try {
          if (l_queueSender != null) {
            l_queueSender.close();
          }
        } catch (Exception var31) {
        }

        if (qs != null) {
          qs.close();
        }
      }

      if (l_transacted) {
        qs.commit();
      }
    } finally {

    }
  }

  /**
   * Read msg from Q.
   *
   * @param p_correlationID the p correlation ID
   * @param p_JMS_QUEUE_ICF the p JM S QUEU E ICF
   * @param p_JMS_QUEUE_PROVIDER_URL the p JM S QUEU E PROVIDE R URL
   * @param p_JMS_QUEUE_SECURITY_PRINCIPAL the p JM S QUEU E SECURIT Y PRINCIPAL
   * @param p_JMS_QUEUE_SECURITY_CREDENTIALS the p JM S QUEU E SECURIT Y CREDENTIALS
   * @param p_JMS_QCF the p JM S QCF
   * @param p_JMS_QUEUE_NAME the p JM S QUEU E NAME
   * @param p_JMS_Q_ACKNOWLEDGE the p JM S Q ACKNOWLEDGE
   * @param p_JMS_Q_TRANSACTION the p JM S Q TRANSACTION
   * @param callerId the caller id
   * @param callerPswd the caller pswd
   * @return the string
   * @throws Exception the exception
   */
  public String readMsgFromQ(
      String p_correlationID,
      String p_JMS_QUEUE_ICF,
      String p_JMS_QUEUE_PROVIDER_URL,
      String p_JMS_QUEUE_SECURITY_PRINCIPAL,
      String p_JMS_QUEUE_SECURITY_CREDENTIALS,
      String p_JMS_QCF,
      String p_JMS_QUEUE_NAME,
      String p_JMS_Q_ACKNOWLEDGE,
      String p_JMS_Q_TRANSACTION,
      String callerId,
      String callerPswd)
      throws Exception {
    String l_reqMsg = null;
    QueueReceiver l_queueReceiver = null;
    TextMessage l_textMessage = null;
    QueueConnection qc = null;
    QueueSession qs = null;
    try {
      InitialContext l_jndiContext = null;
      if (p_JMS_QUEUE_ICF != null && p_JMS_QUEUE_PROVIDER_URL != null) {
        Properties l_jndi_props = new Properties();
        l_jndi_props.put("java.naming.factory.initial", p_JMS_QUEUE_ICF);
        l_jndi_props.put("java.naming.provider.url", p_JMS_QUEUE_PROVIDER_URL);
        if (p_JMS_QUEUE_SECURITY_PRINCIPAL != null && p_JMS_QUEUE_SECURITY_CREDENTIALS != null) {
          l_jndi_props.put("java.naming.security.principal", p_JMS_QUEUE_SECURITY_PRINCIPAL);
          l_jndi_props.put("java.naming.security.credentials", p_JMS_QUEUE_SECURITY_CREDENTIALS);
        }

        l_jndiContext = new InitialContext(l_jndi_props);
      } else {
        l_jndiContext = new InitialContext();
      }

      QueueConnectionFactory l_QCF = (QueueConnectionFactory) l_jndiContext.lookup(p_JMS_QCF);
      Queue l_queue = (Queue) l_jndiContext.lookup(p_JMS_QUEUE_NAME);
      if (callerId != null) {
        qc = l_QCF.createQueueConnection(callerId, callerPswd);
      } else {
        qc = l_QCF.createQueueConnection();
      }

      qc.start();
      boolean l_transacted = false;
      if ("true".equalsIgnoreCase(p_JMS_Q_TRANSACTION)) {
        l_transacted = true;
      }

      int l_acknowledge = 1;
      if ("CLIENT_ACKNOWLEDGE".equalsIgnoreCase(p_JMS_Q_ACKNOWLEDGE)) {
        l_acknowledge = 2;
      } else if ("DUPS_OK_ACKNOWLEDGE".equalsIgnoreCase(p_JMS_Q_ACKNOWLEDGE)) {
        l_acknowledge = 3;
      }

      qs = qc.createQueueSession(l_transacted, l_acknowledge);
      String l_msg;
      if (p_correlationID != null) {
        l_msg = "JMSCorrelationID = '" + p_correlationID + "'";
        l_queueReceiver = qs.createReceiver(l_queue, l_msg);
      } else {
        l_queueReceiver = qs.createReceiver(l_queue);
      }

      boolean isNoWait = true;
      Message receivedMsg;
      if (isNoWait) {
        receivedMsg = l_queueReceiver.receiveNoWait();
      } else {
        receivedMsg = l_queueReceiver.receive(1000L);
      }

      if (receivedMsg != null) {
        if (receivedMsg instanceof TextMessage) {
          l_textMessage = (TextMessage) receivedMsg;
          l_reqMsg = l_textMessage.getText();
        } else {
        }
      } else {
      }
    } finally {
      try {
        if (l_queueReceiver != null) {
          l_queueReceiver.close();
        }
      } catch (Exception var28) {
      }
    }

    return l_reqMsg;
  }

  /**
   * Commit JMS session.
   *
   * @param qs the qs
   * @throws Exception the exception
   */
  public void commitJMSSession(QueueSession qs) throws Exception {
    if (qs != null) {
      qs.commit();
    }
  }

  /**
   * Rollback JMS session.
   *
   * @param qs the qs
   * @throws Exception the exception
   */
  public void rollbackJMSSession(QueueSession qs) throws Exception {
    if (qs != null) {
      qs.rollback();
    }
  }

  /**
   * Close JMS session.
   *
   * @param qs the qs
   * @throws Exception the exception
   */
  public void closeJMSSession(QueueSession qs) throws Exception {
    if (qs != null) {
      qs.close();
      qs = null;
    }
  }

  /**
   * Close JMS connection.
   *
   * @param qc the qc
   * @throws Exception the exception
   */
  public void closeJMSConnection(QueueConnection qc) throws Exception {
    if (qc != null) {
      qc.close();
      qc = null;
    }
  }
}
