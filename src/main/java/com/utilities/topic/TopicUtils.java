package com.utilities.topic;

import java.util.Properties;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

// TODO: Auto-generated Javadoc
/** The Class TopicUtils. */
public class TopicUtils {

  /**
   * Publish msg.
   *
   * @param p_msg the p msg
   * @param p_correlationID the p correlation ID
   * @param p_JMS_TOPIC_ICF the p JM S TOPI C ICF
   * @param p_JMS_TOPIC_PROVIDER_URL the p JM S TOPI C PROVIDE R URL
   * @param p_JMS_TOPIC_SECURITY_PRINCIPAL the p JM S TOPI C SECURIT Y PRINCIPAL
   * @param p_JMS_TOPIC_SECURITY_CREDENTIALS the p JM S TOPI C SECURIT Y CREDENTIALS
   * @param p_JMS_TCF the p JM S TCF
   * @param p_topic_name the p topic name
   * @param p_transacted the p transacted
   * @param l_within_container the l within container
   * @throws Exception the exception
   */
  public void publishMsg(
      String p_msg,
      String p_correlationID,
      String p_JMS_TOPIC_ICF,
      String p_JMS_TOPIC_PROVIDER_URL,
      String p_JMS_TOPIC_SECURITY_PRINCIPAL,
      String p_JMS_TOPIC_SECURITY_CREDENTIALS,
      String p_JMS_TCF,
      String p_topic_name,
      boolean p_transacted,
      boolean l_within_container)
      throws Exception {
    if (p_transacted) {
      p_JMS_TCF = "java:comp/env/" + p_JMS_TCF;
      p_topic_name = "java:comp/env/" + p_topic_name;
    }
    try {
      InitialContext l_jndiContext = null;
      if (p_JMS_TOPIC_ICF != null && p_JMS_TOPIC_PROVIDER_URL != null) {
        Properties l_jndi_props = new Properties();
        if (p_JMS_TOPIC_ICF == null || p_JMS_TOPIC_PROVIDER_URL == null) {
          throw new Exception(
              "Both p_JMS_TOPIC_ICF and p_JMS_TOPIC_PROVIDER_URL should be provided.");
        }

        l_jndi_props.put("java.naming.factory.initial", p_JMS_TOPIC_ICF);
        l_jndi_props.put("java.naming.provider.url", p_JMS_TOPIC_PROVIDER_URL);
        if (p_JMS_TOPIC_SECURITY_PRINCIPAL != null) {
          if (p_JMS_TOPIC_SECURITY_CREDENTIALS == null) {
            throw new Exception(
                "p_JMS_TOPIC_SECURITY_CREDENTIALS should also be provided if p_JMS_TOPIC_SECURITY_PRINCIPAL is provided.");
          }

          l_jndi_props.put("java.naming.security.principal", p_JMS_TOPIC_SECURITY_PRINCIPAL);
          l_jndi_props.put("java.naming.security.credentials", p_JMS_TOPIC_SECURITY_CREDENTIALS);
        }

        l_jndiContext = new InitialContext(l_jndi_props);
      } else {
        l_jndiContext = new InitialContext();
      }

      TopicConnectionFactory l_tcf = (TopicConnectionFactory) l_jndiContext.lookup(p_JMS_TCF);
      Topic l_topic = (Topic) l_jndiContext.lookup(p_topic_name);
      TopicConnection tc = l_tcf.createTopicConnection();
      if (!l_within_container) {
        tc.setClientID("NOTIFY");
      }
      TopicSession ts = tc.createTopicSession(p_transacted, 1);
      TopicPublisher l_publisher = ts.createPublisher(l_topic);
      TextMessage l_textMessage = ts.createTextMessage();
      l_textMessage.setText(p_msg);
      l_publisher.publish(l_textMessage);
    } finally {
    }
  }

  /**
   * Subscribe.
   *
   * @param p_topic_name the p topic name
   * @param p_JMS_TOPIC_ICF the p JM S TOPI C ICF
   * @param p_JMS_TOPIC_PROVIDER_URL the p JM S TOPI C PROVIDE R URL
   * @param p_JMS_TOPIC_SECURITY_PRINCIPAL the p JM S TOPI C SECURIT Y PRINCIPAL
   * @param p_JMS_TOPIC_SECURITY_CREDENTIALS the p JM S TOPI C SECURIT Y CREDENTIALS
   * @param p_JMS_TCF the p JM S TCF
   * @param p_transacted the p transacted
   * @param p_subscription the p subscription
   * @param l_within_container the l within container
   * @return the string
   * @throws Exception the exception
   */
  public String subscribe(
      String p_topic_name,
      String p_JMS_TOPIC_ICF,
      String p_JMS_TOPIC_PROVIDER_URL,
      String p_JMS_TOPIC_SECURITY_PRINCIPAL,
      String p_JMS_TOPIC_SECURITY_CREDENTIALS,
      String p_JMS_TCF,
      boolean p_transacted,
      String p_subscription,
      boolean l_within_container)
      throws Exception {
    String l_reqMsg = null;

    try {
      InitialContext l_jndiContext = null;
      if (p_JMS_TOPIC_ICF != null && p_JMS_TOPIC_PROVIDER_URL != null) {
        Properties l_jndi_props = new Properties();
        if (p_JMS_TOPIC_ICF == null || p_JMS_TOPIC_PROVIDER_URL == null) {
          throw new Exception(
              "Both p_JMS_TOPIC_ICF and p_JMS_TOPIC_PROVIDER_URL should be provided.");
        }

        l_jndi_props.put("java.naming.factory.initial", p_JMS_TOPIC_ICF);
        l_jndi_props.put("java.naming.provider.url", p_JMS_TOPIC_PROVIDER_URL);
        if (p_JMS_TOPIC_SECURITY_PRINCIPAL != null) {
          if (p_JMS_TOPIC_SECURITY_CREDENTIALS == null) {
            throw new Exception(
                "p_JMS_TOPIC_SECURITY_CREDENTIALS should also be provided if p_JMS_TOPIC_SECURITY_PRINCIPAL is provided.");
          }

          l_jndi_props.put("java.naming.security.principal", p_JMS_TOPIC_SECURITY_PRINCIPAL);
          l_jndi_props.put("java.naming.security.credentials", p_JMS_TOPIC_SECURITY_CREDENTIALS);
        }

        l_jndiContext = new InitialContext(l_jndi_props);
      } else {
        l_jndiContext = new InitialContext();
      }
      TopicConnectionFactory l_tcf = (TopicConnectionFactory) l_jndiContext.lookup(p_JMS_TCF);
      Topic l_topic = (Topic) l_jndiContext.lookup(p_topic_name);
      TopicConnection tc = l_tcf.createTopicConnection();
      if (!l_within_container) {
        tc.setClientID("_NOTIFY");
      }
      tc.start();
      TopicSession ts = tc.createTopicSession(p_transacted, 1);
      TopicSubscriber l_subscriber = ts.createDurableSubscriber(l_topic, p_subscription);
      Message l_msg = l_subscriber.receive();
      if (l_msg != null) {
        if (l_msg instanceof TextMessage) {
          TextMessage l_textMessage = (TextMessage) l_msg;
          l_reqMsg = l_textMessage.getText();
        } else {

        }
      } else {

      }
    } finally {

    }

    return l_reqMsg;
  }

  /**
   * Unsubscribe.
   *
   * @param ts the ts
   * @param p_subscription the p subscription
   * @throws Exception the exception
   */
  public void unsubscribe(TopicSession ts, String p_subscription) throws Exception {
    ts.unsubscribe(p_subscription);
  }

  /**
   * Commit JMS session.
   *
   * @param ts the ts
   * @throws Exception the exception
   */
  public void commitJMSSession(TopicSession ts) throws Exception {
    if (ts != null) {
      ts.commit();
    }
  }

  /**
   * Rollback JMS session.
   *
   * @param ts the ts
   * @throws Exception the exception
   */
  public void rollbackJMSSession(TopicSession ts) throws Exception {
    if (ts != null) {
      ts.rollback();
    }
  }

  /**
   * Close JMS session.
   *
   * @param ts the ts
   * @throws Exception the exception
   */
  public void closeJMSSession(TopicSession ts) throws Exception {
    if (ts != null) {
      ts.close();
      ts = null;
    }
  }

  /**
   * Close JMS connection.
   *
   * @param tc the tc
   * @throws Exception the exception
   */
  public void closeJMSConnection(TopicConnection tc) throws Exception {
    if (tc != null) {
      tc.close();
      tc = null;
    }
  }
}
