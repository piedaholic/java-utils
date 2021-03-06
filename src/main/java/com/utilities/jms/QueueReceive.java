package com.utilities.jms;

import java.util.Hashtable;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

// TODO: Auto-generated Javadoc
/**
 * This example shows how to establish a connection to and receive messages from a JMS queue. The
 * classes in this package operate on the same JMS queue. Run the classes together to witness
 * messages being sent and received, and to browse the queue for messages. This class is used to
 * receive and remove messages from the queue.
 *
 * @author Copyright (c) 1999-2005 by BEA Systems, Inc. All Rights Reserved.
 */
public class QueueReceive implements MessageListener {

  /** The Constant JNDI_FACTORY. */
  // Defines the JNDI context factory.
  public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";

  /** The Constant JMS_FACTORY. */
  // Defines the JMS connection factory for the queue.
  public static final String JMS_FACTORY = "jms/TestConnectionFactory";

  /** The Constant QUEUE. */
  // Defines the queue.
  public static final String QUEUE = "jms/TestJMSQueue";

  /** The qcon factory. */
  private QueueConnectionFactory qconFactory;

  /** The qcon. */
  private QueueConnection qcon;

  /** The qsession. */
  private QueueSession qsession;

  /** The qreceiver. */
  private QueueReceiver qreceiver;

  /** The queue. */
  private Queue queue;

  /** The quit. */
  private boolean quit = false;

  /**
   * Message listener interface.
   *
   * @param msg message
   */
  public void onMessage(Message msg) {
    try {
      String msgText;
      if (msg instanceof TextMessage) {
        msgText = ((TextMessage) msg).getText();
      } else {
        msgText = msg.toString();
      }
      System.out.println("Message Received: " + msgText);
      if (msgText.equalsIgnoreCase("quit")) {
        synchronized (this) {
          quit = true;
          this.notifyAll(); // Notify main thread to quit
        }
      }
    } catch (JMSException jmse) {
      System.err.println("An exception occurred: " + jmse.getMessage());
    }
  }

  /**
   * Creates all the necessary objects for receiving messages from a JMS queue.
   *
   * @param ctx JNDI initial context
   * @param queueName name of queue
   * @exception NamingException if operation cannot be performed
   * @exception JMSException if JMS fails to initialize due to internal error
   */
  public void init(Context ctx, String queueName) throws NamingException, JMSException {
    qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
    qcon = qconFactory.createQueueConnection();
    qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    queue = (Queue) ctx.lookup(queueName);
    qreceiver = qsession.createReceiver(queue);
    qreceiver.setMessageListener(this);
    qcon.start();
  }

  /**
   * Closes JMS objects.
   *
   * @exception JMSException if JMS fails to close objects due to internal error
   */
  public void close() throws JMSException {
    qreceiver.close();
    qsession.close();
    qcon.close();
  }

  /**
   * main() method.
   *
   * @param args WebLogic Server URL
   * @exception Exception if execution fails
   */
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("Usage: java examples.jms.queue.QueueReceive WebLogicURL");
      return;
    }
    InitialContext ic = getInitialContext(args[0]);
    QueueReceive qr = new QueueReceive();
    qr.init(ic, QUEUE);
    System.out.println("JMS Ready To Receive Messages (To quit, send a \"quit\" message).");
    // Wait until a "quit" message has been received.
    synchronized (qr) {
      while (!qr.quit) {
        try {
          qr.wait();
        } catch (InterruptedException ie) {
        }
      }
    }
    qr.close();
  }

  /**
   * Gets the initial context.
   *
   * @param url the url
   * @return the initial context
   * @throws NamingException the naming exception
   */
  private static InitialContext getInitialContext(String url) throws NamingException {
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
    env.put(Context.PROVIDER_URL, url);
    return new InitialContext(env);
  }
}
