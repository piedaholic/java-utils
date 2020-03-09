package com.utilities.mbean;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

public class MBeanManager {
    private static MBeanManager mBeanManager;
    private MBeanServer _mbeanServer;
    private String _beanName = "Mbean:name=";
    private String _defaultName = "Generic";
    private Collection<String> _mbeanNames = new ArrayList<String>();

    public static MBeanManager getInstance() {
	if (mBeanManager == null) {
	    mBeanManager = new MBeanManager();
	}

	return mBeanManager;
    }

    public String registerNewBean(String name, AppBean bean) {
	String ret = null;
	String beanName = this.getName(name);

	try {
	    ObjectName obj = new ObjectName(beanName);
	    ObjectInstance newobj = this.getMBeanServer().registerMBean(bean, obj);
	    ret = newobj.getObjectName().getCanonicalName();
	    this._mbeanNames.add(name);
	} catch (MalformedObjectNameException var8) {
	    ret = "Exception, " + var8.getClass().getName() + ", " + var8.getMessage();
	} catch (InstanceAlreadyExistsException var9) {
	    ret = "Exception, " + var9.getClass().getName() + ", " + var9.getMessage();
	} catch (MBeanRegistrationException var10) {
	    ret = "Exception, " + var10.getClass().getName() + ", " + var10.getMessage();
	} catch (NotCompliantMBeanException var11) {
	    ret = "Exception, " + var11.getClass().getName() + ", " + var11.getMessage();
	} catch (Exception var12) {
	    ret = "Exception, " + var12.getClass().getName() + ", " + var12.getMessage();
	}

	return ret;
    }

    public Object getAttributeValue(String beanName, String attributeName) {
	Object ret = null;

	try {
	    ObjectName obj = new ObjectName(this.getName(beanName));
	    MBeanInfo info = this._mbeanServer.getMBeanInfo(obj);
	    System.out.println("MBeanInfo" + info);
	    Object val = this._mbeanServer.getAttribute(obj, attributeName);
	    ret = val;
	} catch (Exception var6) {
	}

	return ret;
    }

    public Object execute(String beanName, String methodName, String targetAtrribute) {
	Object ret = null;

	try {
	    ObjectName obj = new ObjectName(this.getName(beanName));
	    MBeanInfo info = this._mbeanServer.getMBeanInfo(obj);
	    System.out.println("MBeanInfo" + info);
	    this._mbeanServer.invoke(obj, methodName, (Object[]) null, (String[]) null);
	    Object val = this._mbeanServer.getAttribute(obj, targetAtrribute);
	    ret = val;
	} catch (Exception var6) {
	}

	return ret;
    }

    public void callToMBean(List<?> mBeanList)
	    throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
	    NoSuchMethodException, SecurityException, ClassNotFoundException {
	Iterator<?> mBeanListIterator = mBeanList.iterator();
	while (mBeanListIterator.hasNext()) {
	    String mBeanValues = (String) mBeanListIterator.next();
	    Object[] params = new Object[] {};
	    String[] signature = new String[] {};
	    String[] mBeanValue = mBeanValues.split("-");
	    String name = "";
	    AppBean bean = (AppBean) Class.forName(mBeanValue[0]).getConstructor().newInstance();
	    int i;
	    for (i = 0; i < mBeanValue.length - 1; i++)
		name = name + mBeanValue[i] + "-";
	    String[] attributeValues = mBeanValue[mBeanValue.length - 1].split("~");
	    for (i = 0; i < attributeValues.length; i++) {
		String[] dataTypes = bean.getAttributeDataType().split("~");
		for (String dataType : dataTypes) {
		    appendValue(signature, dataType);
		    if (dataType == "String")
			appendValue(params, (String) attributeValues[i]);
		    else if (dataType == "Date") {
			appendValue(params, attributeValues[i]);
		    } else if (dataType == "int") {
			appendValue(params, Integer.parseInt(attributeValues[i]));
		    } else if (dataType == "double") {
			appendValue(params, Double.parseDouble(attributeValues[i]));
		    } else if (dataType == "float") {
			appendValue(params, Float.parseFloat(attributeValues[i]));
		    }
		}
	    }
	    try {
		ObjectName obj = new ObjectName(this.getName(name));
		this._mbeanServer.getMBeanInfo(obj);
		if (!this.getMBeanServer().isRegistered(obj)) {
		    this.registerNewBean(name, bean);
		}
		this._mbeanServer.invoke(obj, "updateBean", params, signature);
	    } catch (Exception var22) {
	    }
	}

    }

    private String getName(String name) {
	String beanName;
	if (!name.equals("") && name != null) {
	    beanName = this._beanName + name;
	} else {
	    beanName = this._beanName + this._defaultName;
	}

	return beanName;
    }

    private MBeanServer getMBeanServer() {
	if (this._mbeanServer == null) {
	    this._mbeanServer = MBeanServerFactory.createMBeanServer();
	}

	return this._mbeanServer;
    }

    public void executeToplink(String[] attributes) {
	Object[] elements = this._mbeanNames.toArray();
	List<Object> mbeanList = new ArrayList<Object>();

	try {
	    for (int i = 0; i < elements.length; ++i) {
		String registeredBeanName = (String) elements[i];
		ObjectName obj = new ObjectName(this.getName(registeredBeanName));
		String[] fas = registeredBeanName.split("-");
		String beanName = "";

		for (int j = 0; j < fas.length; ++j) {
		    beanName = beanName + fas[j] + "~";
		}

		for (i = 0; i < attributes.length; i++) {
		    Object attrObj = this._mbeanServer.getAttribute(obj, attributes[i]);
		    beanName = beanName + attrObj + "~";
		}
		beanName = beanName + this.timeStamp() + "~";
		if (this._mbeanNames.contains(elements[i])) {
		    this._mbeanNames.remove(elements[i]);
		}

		this._mbeanServer.unregisterMBean(obj);
	    }

	    if (mbeanList.size() > 0) {
		// TO-DO
		// Save MBean
	    }
	} catch (Exception var15) {
	}

    }

    public String timeStamp() {
	Date myDate = new Date();
	String strDate = "";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
	strDate = formatter.format(myDate);
	return strDate;
    }

    public String getListFromMbeanServer(String[] attributes) {
	String mbeanServerList = "";
	Object[] elements = this._mbeanNames.toArray();

	for (int i = 0; i < elements.length; ++i) {
	    String registeredBeanName = (String) elements[i];

	    try {
		ObjectName obj = new ObjectName(this.getName(registeredBeanName));
		String[] fas = registeredBeanName.split("-");
		for (i = 0; i < fas.length; i++) {
		    mbeanServerList = mbeanServerList + fas[i] + "!";
		}
		for (i = 0; i < attributes.length; i++) {
		    Object attrObj = this._mbeanServer.getAttribute(obj, attributes[i]);
		    mbeanServerList = mbeanServerList + attrObj + "!";
		}
		mbeanServerList = mbeanServerList + "~";
	    } catch (Exception var17) {
	    }
	}

	return mbeanServerList;
    }

    private Object[] appendValue(Object[] obj, Object newObj) {

	ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
	temp.add(newObj);
	return temp.toArray();

    }
}
