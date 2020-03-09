package com.utilities.ejb;

import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

public class EjbUtils {

    public Object getEJBInstance(String jndiName, InitialContext initialContext) {
	Object ejbObject = null;
	try {
	    ejbObject = initialContext.lookup("java:comp/env/" + jndiName);
	} catch (NamingException ne) {
	}
	return ejbObject;
    }

    public Object getEJBRemoteInstance(String jndiName, InitialContext initialContext) {
	Object ejbObject = null;
	try {
	    ejbObject = initialContext.lookup(jndiName);
	} catch (NamingException ne) {

	}
	return ejbObject;
    }

    public EJBHome getEJBHome(String jndiName, Map<String, EJBHome> cache, InitialContext initialContext) {
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

    public EJBLocalHome getEJBLocalHome(String jndiName, InitialContext initialContext,
	    Map<String, EJBLocalHome> cache) {
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

    public DataSource getDataSource(String dataSourceName, InitialContext initialContext,
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

    public DataSource getDataSource(String dataSourceName, String entityId, InitialContext initialContext,
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

    public EJBHome getSchedEJBHome(String jndiName, Class<? extends EJBHome> clazz, InitialContext initialContext,
	    Map<String, EJBHome> cache, String initCtxFactory) {
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
