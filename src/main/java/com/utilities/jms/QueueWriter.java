package com.utilities.jms;

//import javax.jms.QueueReceiver;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.Map;
import javax.jms.QueueSender;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
//import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import java.util.Hashtable;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.QueueSession;
import javax.jms.QueueConnection;

public class QueueWriter {
	private static String g_className = "QueueWriter";
	// private GWLogger g_gwLog;
	private static QueueConnection g_QueueConn;
	private static QueueSession g_QueueSession;
	private static String g_JMSMsgID;
	// Main Function Arguments Start
	private static String RANDOM_ALG_NAME = "SHA1PRNG";
	private static Object messageObj = "Harsh was here";
	private static String p_correlationID;
	private static String p_JMS_QUEUE_ICF = "weblogic.jndi.WLInitialContextFactory";
	private static String p_JMS_QUEUE_PROVIDER_URL = "t3://localhost:7003";
	private static String p_JMS_QUEUE_SECURITY_PRINCIPAL = "weblogic";
	private static String p_JMS_QUEUE_SECURITY_CREDENTIALS = "weblogic123";
	private static String p_JMS_QCF = "AIRQCF";
	private static String p_JMS_QUEUE_NAME = "CBT_FCUBS_AIR_BATCH";
	private static String p_JMS_Q_ACKNOWLEDGE = "AUTO_ACKNOWLEDGE";
	private static String p_JMS_Q_TRANSACTION = "FALSE";
	private static int p_JMS_Q_DELIVERY_OPT = 2;
	private static int p_JMS_Q_TIME_TO_LIVE = 0;
	private static int p_JMS_Q_PRIORITY = 7;
	private static String callerID = "weblogic";
	private static String callerPswd = "weblogic123";
	private static Queue g_Queue;
	private static QueueConnectionFactory g_QCF;
	private static InitialContext g_InitialCtx;
	private static QueueSender g_QueueSender = null;
	// Main Function Arguments End

	public static void closeJMSConnection() throws Exception {
		if (g_QueueConn != null) {
			g_QueueConn.close();
			g_QueueConn = null;
		}
	}

	public static void closeJMSSession() throws Exception {
		if (g_QueueSession != null) {
			g_QueueSession.close();
			g_QueueSession = null;
		}
	}

	public static void commitJMSSession() throws Exception {
		if (g_QueueSession != null) {
			g_QueueSession.commit();
		}
	}

	public static void main(String[] args) throws Exception {
		g_className = "QueueWriter";
		g_QueueConn = null;
		g_QueueSession = null;
		g_JMSMsgID = null;
		String l_methodName = "sendMsgToQ";
		String strMessage = null;
		boolean isStringMsg = false;
		Message jmsMessage = null;
		if (messageObj instanceof String) {
			strMessage = (String) messageObj;
			isStringMsg = true;
		} else if (messageObj instanceof TextMessage) {
			jmsMessage = (Message) messageObj;
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
		setInitialContext();
		try {
			int l_acknowledge = 1;
			if ("CLIENT_ACKNOWLEDGE".equalsIgnoreCase(p_JMS_Q_ACKNOWLEDGE)) {
				l_acknowledge = 2;
			} else if ("DUPS_OK_ACKNOWLEDGE".equalsIgnoreCase(p_JMS_Q_ACKNOWLEDGE)) {
				l_acknowledge = 3;
			}
			g_QueueSession = g_QueueConn.createQueueSession(l_transacted, l_acknowledge);
			System.out.println(g_className + "." + l_methodName + "Creating Queue Sender");
			g_QueueSender = g_QueueSession.createSender(g_Queue);
			setCorrelationID();
			System.out.println(g_className + "." + l_methodName + "Sending message to Queue with JMS Correlation ID as "
					+ p_correlationID);

			if (isStringMsg) {
				TextMessage l_textMessage = g_QueueSession.createTextMessage();
				l_textMessage.setText(strMessage);
				l_textMessage.setJMSCorrelationID(p_correlationID);
				jmsMessage = (Message) l_textMessage;
			}
			g_QueueSender.send(jmsMessage, p_JMS_Q_DELIVERY_OPT, p_JMS_Q_PRIORITY, (long) p_JMS_Q_TIME_TO_LIVE);
			setJMSMsgID(jmsMessage.getJMSMessageID());
			System.out.println(g_className + "." + l_methodName + "JMS Message ID is " + g_JMSMsgID);
			if (l_transacted) {
				commitJMSSession();
			}
			return;
		} finally {
			try {
				if (g_QueueSender != null) {
					g_QueueSender.close();
				}
				closeJMSConnection();
				closeJMSSession();
			} catch (Exception ex) {
				// this.g_gwLog.printStackTrace(g_className + l_methodName + ex);
				System.out.println(ex);
			}
		}
	}

	private static void setCorrelationID() {
		String l_methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("EMSInEJBBean" + l_methodName + "Generating random Message Correlation ID");
		SecureRandom r;
		try {
			r = SecureRandom.getInstance(RANDOM_ALG_NAME);
			int rNo = 0;
			rNo = r.nextInt();
			rNo = ((rNo > 0) ? rNo : (-1 * rNo));
			p_correlationID = String.valueOf(rNo);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}

	}

	private static void setInitialContext() throws Exception {
		String l_methodName = "setInitialContext()";
		Hashtable<String, String> l_jndi_props = new Hashtable<String, String>();
		// l_jndi_props.put("java.naming.factory.initial", p_JMS_QUEUE_ICF);
		// l_jndi_props.put("java.naming.provider.url", p_JMS_QUEUE_PROVIDER_URL);
		l_jndi_props.put(Context.INITIAL_CONTEXT_FACTORY, p_JMS_QUEUE_ICF);
		l_jndi_props.put(Context.PROVIDER_URL, p_JMS_QUEUE_PROVIDER_URL);
		if (p_JMS_QUEUE_SECURITY_PRINCIPAL != null) {
			if (p_JMS_QUEUE_SECURITY_CREDENTIALS == null) {
				throw new Exception(
						"p_JMS_QUEUE_SECURITY_CREDENTIALS should also be provided if p_JMS_QUEUE_SECURITY_PRINCIPAL is provided.");
			}
			l_jndi_props.put("java.naming.security.principal", p_JMS_QUEUE_SECURITY_PRINCIPAL);
			l_jndi_props.put("java.naming.security.credentials", p_JMS_QUEUE_SECURITY_CREDENTIALS);
		}
		g_InitialCtx = new InitialContext(l_jndi_props);
		g_QCF = (QueueConnectionFactory) g_InitialCtx.lookup(p_JMS_QCF);
		System.out.println(g_className + "." + l_methodName + "------>" + "Looking up the Queue : " + p_JMS_QUEUE_NAME);
		if (p_JMS_QUEUE_NAME.indexOf("queue://") != -1) {
			p_JMS_QUEUE_NAME = p_JMS_QUEUE_NAME.substring(p_JMS_QUEUE_NAME.lastIndexOf("/") + 1);
		}
		g_Queue = (Queue) g_InitialCtx.lookup(p_JMS_QUEUE_NAME);
		System.out.println(g_className + g_InitialCtx + "Creating Queue Connection");
		if (g_QueueConn == null) {
			if (callerID != null) {
				g_QueueConn = g_QCF.createQueueConnection(callerID, callerPswd);
			} else {
				g_QueueConn = g_QCF.createQueueConnection();
			}
		}
		System.out.println(g_className + l_methodName + "Creating Queue Session");

	}

	protected static void setJMSMsgID(String p_JMSMsgID) {
		g_JMSMsgID = p_JMSMsgID;
	}

	public String getJMSMsgID() {
		return g_JMSMsgID;
	}

	public void rollbackJMSSession() throws Exception {
		if (g_QueueSession != null) {
			g_QueueSession.rollback();
		}
	}
}