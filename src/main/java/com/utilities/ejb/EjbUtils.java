package com.utilities.ejb;

import java.util.Map;
import java.util.Properties;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

// TODO: Auto-generated Javadoc
/** The Class EjbUtils. */
public class EjbUtils {

  /**
   * Gets the EJB instance.
   *
   * @param jndiName the jndi name
   * @param initialContext the initial context
   * @return the EJB instance
   */
  public Object getEJBInstance(String jndiName, InitialContext initialContext) {
    Object ejbObject = null;
    try {
      ejbObject = initialContext.lookup("java:comp/env/" + jndiName);
    } catch (NamingException ne) {
    }
    return ejbObject;
  }

  /**
   * Gets the EJB remote instance.
   *
   * @param jndiName the jndi name
   * @param initialContext the initial context
   * @return the EJB remote instance
   */
  public Object getEJBRemoteInstance(String jndiName, InitialContext initialContext) {
    Object ejbObject = null;
    try {
      ejbObject = initialContext.lookup(jndiName);
    } catch (NamingException ne) {

    }
    return ejbObject;
  }

  /**
   * Gets the EJB home.
   *
   * @param jndiName the jndi name
   * @param cache the cache
   * @param initialContext the initial context
   * @return the EJB home
   */
  public EJBHome getEJBHome(
      String jndiName, Map<String, EJBHome> cache, InitialContext initialContext) {
    EJBHome ejbHome = null;
    try {
      if (cache.containsKey(jndiName)) {
        ejbHome = (EJBHome) cache.get(jndiName);
      } else {
        ejbHome = (EJBHome) initialContext.lookup("java:comp/env/" + jndiName);
        cache.put(jndiName, ejbHome);
      }
    } catch (NamingException ne) {
    }
    return ejbHome;
  }

  /**
   * Gets the EJB local home.
   *
   * @param jndiName the jndi name
   * @param initialContext the initial context
   * @param cache the cache
   * @return the EJB local home
   */
  public EJBLocalHome getEJBLocalHome(
      String jndiName, InitialContext initialContext, Map<String, EJBLocalHome> cache) {
    EJBLocalHome ejbHome = null;
    try {
      if (cache.containsKey(jndiName)) {
        ejbHome = (EJBLocalHome) cache.get(jndiName);
      } else {
        if (jndiName.indexOf("java:global") > -1) {
          ejbHome = (EJBLocalHome) initialContext.lookup(jndiName);
        } else {
          ejbHome = (EJBLocalHome) initialContext.lookup("java:comp/env/" + jndiName);
        }
        cache.put(jndiName, ejbHome);
      }
    } catch (NamingException ne) {

    }
    return ejbHome;
  }

  /**
   * Gets the data source.
   *
   * @param dataSourceName the data source name
   * @param initialContext the initial context
   * @param cache the cache
   * @return the data source
   */
  public DataSource getDataSource(
      String dataSourceName, InitialContext initialContext, Map<String, DataSource> cache) {
    DataSource dataSource = null;
    if (dataSourceName.indexOf("jdbc/") == -1) {
      return null;
    }
    try {
      if (cache.containsKey(dataSourceName)) {
        dataSource = (DataSource) cache.get(dataSourceName);
      } else {
        dataSource = (DataSource) initialContext.lookup(dataSourceName);
        cache.put(dataSourceName, dataSource);
      }
    } catch (NamingException ne) {
    }
    return dataSource;
  }

  /**
   * Gets the data source.
   *
   * @param dataSourceName the data source name
   * @param entityId the entity id
   * @param initialContext the initial context
   * @param cache the cache
   * @return the data source
   */
  public DataSource getDataSource(
      String dataSourceName,
      String entityId,
      InitialContext initialContext,
      Map<String, DataSource> cache) {
    DataSource dataSource = null;
    if (dataSourceName.indexOf("jdbc/") == -1) {
      return null;
    }
    try {
      if (cache.containsKey(dataSourceName)) {
        dataSource = (DataSource) cache.get(dataSourceName);
      } else {
        dataSource = (DataSource) initialContext.lookup(dataSourceName);
        cache.put(dataSourceName, dataSource);
      }
    } catch (NamingException ne) {
    }
    return dataSource;
  }

  /**
   * Gets the sched EJB home.
   *
   * @param jndiName the jndi name
   * @param clazz the clazz
   * @param initialContext the initial context
   * @param cache the cache
   * @param initCtxFactory the init ctx factory
   * @return the sched EJB home
   */
  public EJBHome getSchedEJBHome(
      String jndiName,
      Class<? extends EJBHome> clazz,
      InitialContext initialContext,
      Map<String, EJBHome> cache,
      String initCtxFactory) {
    EJBHome ejbHome = null;
    try {
      if (cache.containsKey(jndiName)) {
        ejbHome = (EJBHome) cache.get(jndiName);
      } else {
        Properties prop = new Properties();
        prop.setProperty("java.naming.factory.initial", initCtxFactory);

        InitialContext initConxt = new InitialContext(prop);
        Object rawObj = initConxt.lookup(jndiName);
        ejbHome = (EJBHome) PortableRemoteObject.narrow(rawObj, clazz);

        cache.put(jndiName, ejbHome);
      }
    } catch (NamingException ne) {

    }
    return ejbHome;
  }
}
