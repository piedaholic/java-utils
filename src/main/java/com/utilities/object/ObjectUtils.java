package com.utilities.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.utilities.string.StringUtils;

public class ObjectUtils {
    public byte[] serializeObject(Object object) {
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	try {
	    ObjectOutputStream stream = new ObjectOutputStream(bos);
	    stream.writeObject(object);
	} catch (IOException localIOException) {
	}
	return bos.toByteArray();
    }

    public Object getObject(byte[] byteArray) throws Exception {
	ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteArray));

	Object obj = ois.readObject();
	ois.close();
	return obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T checkForNull(T input) {
	T output = null;
	if (input != null) {
	    if (input instanceof String)
		output = (T) ((String) input).trim();
	}
	return output;
    }

    public static <T> void copyObject(Object sourceClass, Object targetClass) {
	Method[] srcMethods = sourceClass.getClass().getMethods();
	Object srcValue = null;
	for (Method srcMethod : srcMethods) {
	    if (srcMethod.getName().startsWith("get") && Character.isUpperCase(srcMethod.getName().charAt(3))
		    && srcMethod.getName() != "getClass") {
		try {
		    srcValue = srcMethod.invoke(sourceClass);
		} catch (Exception e) {
		    // System.out.println(Logger.getInstance().writeStack(e));
		}
		try {
		    if (doesMethodExist(targetClass, srcMethod.getName())) {
			Method targetMethod = targetClass.getClass()
				.getMethod(srcMethod.getName().replace("get", "set"), srcMethod.getReturnType());
			targetMethod.invoke(targetClass, srcValue);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    try {
			Method targetMethod = targetClass.getClass()
				.getMethod(srcMethod.getName().replace("get", "set"), String.class);
			targetMethod.invoke(targetClass, srcValue.toString());
		    } catch (Exception e1) {
			e.printStackTrace();
		    }
		}
	    }
	}
    }

    public static <T> void setObject(Object targetClass, String methodName, Object arg) {
	try {
	    Class<?> argClass = getDataType((String) arg);
	    Object tempArg = getValue((String) arg, argClass.getName());
	    Method method = targetClass.getClass().getDeclaredMethod(methodName, argClass);
	    method.invoke(targetClass, new Object[] { tempArg });
	} catch (Exception e) {
	    e.printStackTrace();
	    try {
		Method method = targetClass.getClass().getDeclaredMethod(methodName, String.class);
		method.invoke(targetClass, new Object[] { arg.toString() });
	    } catch (Exception e1) {
		e.printStackTrace();
	    }
	}
    }

    public static <T> void setList(Object sourceClass, String methodName, Object arg) {
	try {
	    Class<?> argClass = List.class;// arg.getClass();
	    Method method = sourceClass.getClass().getDeclaredMethod(methodName, argClass);
	    method.invoke(sourceClass, new Object[] { arg });
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static <T> void setCompositeObject(Object sourceClass, String methodName, Object arg) {
	try {
	    Class<?> argClass = arg.getClass();
	    Method method = sourceClass.getClass().getDeclaredMethod(methodName, argClass);
	    method.invoke(sourceClass, new Object[] { arg });
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static boolean doesMethodExist(Object classObj, String methodName) {
	// if (!hashMap.containsKey(classObj.getClass() + "~" + methodName)) {
	Method[] methods = classObj.getClass().getMethods();
	for (Method method : methods) {
	    // if (!hashMap.containsKey(classObj.getClass() + "~" + method.getName()))
	    // hashMap.put(classObj.getClass() + "~" + method.getName(), true);
	    if (method.getName() == methodName || method.getName().equals(methodName)) {
		// hashMap.put(classObj.getClass() + "~" + methodName, true);
		return true;
	    }
	}
	// hashMap.put(classObj.getClass() + "~" + methodName, false);
	return false;
	// } else {
	// return hashMap.get(classObj.getClass() + "~" + methodName);
	// }
    }

    public static String listToString(List<?> list) {
	String result = "";
	if (list != null) {
	    Iterator<?> iterator = list.iterator();
	    while (iterator.hasNext()) {
		result = "[" + iterator.next().toString() + "]";
	    }
	}
	return result;
    }

    public static boolean isNull(Object obj) {
	boolean result = false;
	try {
	    if (obj == null || obj.equals(null))
		result = true;
	} catch (NullPointerException e) {
	    result = true;
	}
	return result;
    }

    public static Object getValue(String value, String dataType) throws Exception {
	if ("".equals(value)) {
	    value = null;
	}
	if (dataType.equals("java.lang.String")) {
	    return value;
	}
	if ("java.math.BigDecimal".equals(dataType)) {
	    return ConversionUtils.getValueAsBigDecimal(value);
	}
	if ("java.util.Date".equals(dataType)) {
	    return ConversionUtils.getValueAsDate(value);
	}
	if ("java.sql.Timestamp".equals(dataType)) {
	    return null;
	}
	if ("NUMBER".equals(dataType)) {
	    return ConversionUtils.getValueAsBigDecimal(value);
	}
	if (("VARCHAR2".equals(dataType)) || ("CHAR".equals(dataType))) {
	    return value;
	}
	if ("DATE".equals(dataType)) {
	    return ConversionUtils.getValueAsDate(value);
	}
	if ("TIMESTAMP".equals(dataType)) {
	    return ConversionUtils.getValueAsTimestamp(value);
	}
	if ("LONG".equals(dataType)) {
	    return value;
	}
	return null;
    }

    public static Class<?> getDataType(String data) {
	if (StringUtils.isInteger(data) || StringUtils.isDouble(data) || StringUtils.isFloat(data))
	    return BigDecimal.class;
	else if (StringUtils.isDateTimestampValid(data)) {
	    return Date.class;
	} else {
	    return String.class;
	}
    }
}
