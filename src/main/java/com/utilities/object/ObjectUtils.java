package com.utilities.object;

import com.utilities.exceptions.CloneFailedException;
import com.utilities.string.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/** The Class ObjectUtils. */
public class ObjectUtils {

  /** The package separator character: {@code '&#x2e;' == {@value}}. */
  public static final char PACKAGE_SEPARATOR_CHAR = '.';

  /** The package separator String: {@code "&#x2e;"}. */
  public static final String PACKAGE_SEPARATOR = String.valueOf(PACKAGE_SEPARATOR_CHAR);

  /** The inner class separator character: {@code '$' == {@value}}. */
  public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

  /** The inner class separator String: {@code "$"}. */
  public static final String INNER_CLASS_SEPARATOR = String.valueOf(INNER_CLASS_SEPARATOR_CHAR);

  /** Maps names of primitives to their corresponding primitive {@code Class}es. */
  private static final Map<String, Class<?>> namePrimitiveMap = new HashMap<>();

  static {
    namePrimitiveMap.put("boolean", Boolean.TYPE);
    namePrimitiveMap.put("byte", Byte.TYPE);
    namePrimitiveMap.put("char", Character.TYPE);
    namePrimitiveMap.put("short", Short.TYPE);
    namePrimitiveMap.put("int", Integer.TYPE);
    namePrimitiveMap.put("long", Long.TYPE);
    namePrimitiveMap.put("double", Double.TYPE);
    namePrimitiveMap.put("float", Float.TYPE);
    namePrimitiveMap.put("void", Void.TYPE);
  }

  /** Maps primitive {@code Class}es to their corresponding wrapper {@code Class}. */
  private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();

  static {
    primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
    primitiveWrapperMap.put(Byte.TYPE, Byte.class);
    primitiveWrapperMap.put(Character.TYPE, Character.class);
    primitiveWrapperMap.put(Short.TYPE, Short.class);
    primitiveWrapperMap.put(Integer.TYPE, Integer.class);
    primitiveWrapperMap.put(Long.TYPE, Long.class);
    primitiveWrapperMap.put(Double.TYPE, Double.class);
    primitiveWrapperMap.put(Float.TYPE, Float.class);
    primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
  }

  /** Maps wrapper {@code Class}es to their corresponding primitive types. */
  private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<>();

  static {
    for (final Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperMap.entrySet()) {
      final Class<?> primitiveClass = entry.getKey();
      final Class<?> wrapperClass = entry.getValue();
      if (!primitiveClass.equals(wrapperClass)) {
        wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
      }
    }
  }

  /** Maps a primitive class name to its corresponding abbreviation used in array class names. */
  @SuppressWarnings("unused")
  private static final Map<String, String> abbreviationMap;

  /** Maps an abbreviation used in array class names to corresponding primitive class name. */
  @SuppressWarnings("unused")
  private static final Map<String, String> reverseAbbreviationMap;
  // Feed abbreviation maps
  static {
    final Map<String, String> m = new HashMap<>();
    m.put("int", "I");
    m.put("boolean", "Z");
    m.put("float", "F");
    m.put("long", "J");
    m.put("short", "S");
    m.put("byte", "B");
    m.put("double", "D");
    m.put("char", "C");
    final Map<String, String> r = new HashMap<>();
    for (final Map.Entry<String, String> e : m.entrySet()) {
      r.put(e.getValue(), e.getKey());
    }
    abbreviationMap = Collections.unmodifiableMap(m);
    reverseAbbreviationMap = Collections.unmodifiableMap(r);
  }

  /**
   * Serialize object.
   *
   * @param object the object
   * @return the byte[]
   */
  public byte[] serializeObject(Object object) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ObjectOutputStream stream = new ObjectOutputStream(bos);
      stream.writeObject(object);
    } catch (IOException localIOException) {
    }
    return bos.toByteArray();
  }

  /**
   * Gets the object.
   *
   * @param byteArray the byte array
   * @return the object
   * @throws Exception the exception
   */
  public Object getObject(byte[] byteArray) throws Exception {
    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteArray));
    Object obj = ois.readObject();
    ois.close();
    return obj;
  }

  /**
   * Copy object.
   *
   * @param <T> the generic type
   * @param sourceObject the source object
   * @param targetObject the target object
   */
  public static <T> void copyObject(Object sourceObject, Object targetObject) {
    Method[] srcMethods = sourceObject.getClass().getMethods();
    Object srcValue = null;
    for (Method srcMethod : srcMethods) {
      // Invoke getter method of source object
      // Store the value in variable srcValue
      // Exclude getClass() method
      // Check if fourth character of the method is in uppercase
      if (srcMethod.getName().startsWith("get")
          && Character.isUpperCase(srcMethod.getName().charAt(3))
          && srcMethod.getName() != "getClass") {
        try {
          srcValue = srcMethod.invoke(sourceObject);
        } catch (Exception e) {
          // System.out.println(Logger.getInstance().writeStack(e));
        }
        try {
          if (ClassUtils.doesMethodExist(targetObject, srcMethod.getName())) {
            // Fetch setter method from getter method by replacing get with set
            Method targetMethod =
                targetObject
                    .getClass()
                    .getMethod(
                        srcMethod.getName().replace("get", "set"), srcMethod.getReturnType());
            targetMethod.invoke(targetObject, srcValue);
          }
        } catch (Exception e) {
          e.printStackTrace();
          try {
            Method targetMethod =
                targetObject
                    .getClass()
                    .getMethod(srcMethod.getName().replace("get", "set"), String.class);
            targetMethod.invoke(targetObject, srcValue.toString());
          } catch (Exception e1) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Sets the object.
   *
   * @param <T> the generic type
   * @param targetClass the target class
   * @param methodName the method name
   * @param arg the arg
   */
  public static <T> void setObject(Object targetClass, String methodName, Object arg) {
    try {
      Class<?> argClass = getDataType((String) arg);
      Object tempArg = getValue((String) arg, argClass.getName());
      Method method = targetClass.getClass().getDeclaredMethod(methodName, argClass);
      method.invoke(targetClass, new Object[] {tempArg});
    } catch (Exception e) {
      e.printStackTrace();
      try {
        Method method = targetClass.getClass().getDeclaredMethod(methodName, String.class);
        method.invoke(targetClass, new Object[] {arg.toString()});
      } catch (Exception e1) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Sets the list.
   *
   * @param <T> the generic type
   * @param sourceClass the source class
   * @param methodName the method name
   * @param arg the arg
   */
  public static <T> void setList(Object sourceClass, String methodName, Object arg) {
    try {
      Class<?> argClass = List.class; // arg.getClass();
      Method method = sourceClass.getClass().getDeclaredMethod(methodName, argClass);
      method.invoke(sourceClass, new Object[] {arg});
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the composite object.
   *
   * @param <T> the generic type
   * @param sourceClass the source class
   * @param methodName the method name
   * @param arg the arg
   */
  public static <T> void setCompositeObject(Object sourceClass, String methodName, Object arg) {
    try {
      Class<?> argClass = arg.getClass();
      Method method = sourceClass.getClass().getDeclaredMethod(methodName, argClass);
      method.invoke(sourceClass, new Object[] {arg});
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if is null.
   *
   * @param <T> the generic type
   * @param obj the obj
   * @return true, if is null
   */
  public static <T> boolean isNull(T obj) {
    boolean result = false;
    if (obj == null) result = true;
    else if (obj instanceof String) return ((String) obj).length() == 0;
    return result;
  }

  /**
   * Primitive to wrapper.
   *
   * @param cls the cls
   * @return the class
   */
  public static Class<?> primitiveToWrapper(final Class<?> cls) {
    Class<?> convertedClass = cls;
    if (cls != null && cls.isPrimitive()) {
      convertedClass = primitiveWrapperMap.get(cls);
    }
    return convertedClass;
  }

  /**
   * Wrapper to primitive.
   *
   * @param cls the cls
   * @return the class
   */
  public static Class<?> wrapperToPrimitive(final Class<?> cls) {
    return wrapperPrimitiveMap.get(cls);
  }

  /**
   * Wrappers to primitives.
   *
   * @param classes the classes
   * @return the class[]
   */
  public static Class<?>[] wrappersToPrimitives(final Class<?>... classes) {
    if (classes == null) {
      return null;
    }

    if (classes.length == 0) {
      return classes;
    }

    final Class<?>[] convertedClasses = new Class[classes.length];
    for (int i = 0; i < classes.length; i++) {
      convertedClasses[i] = wrapperToPrimitive(classes[i]);
    }
    return convertedClasses;
  }

  /**
   * Primitives to wrappers.
   *
   * @param classes the classes
   * @return the class[]
   */
  public static Class<?>[] primitivesToWrappers(final Class<?>... classes) {
    if (classes == null) {
      return null;
    }

    if (classes.length == 0) {
      return classes;
    }

    final Class<?>[] convertedClasses = new Class[classes.length];
    for (int i = 0; i < classes.length; i++) {
      convertedClasses[i] = primitiveToWrapper(classes[i]);
    }
    return convertedClasses;
  }

  /**
   * Gets the value.
   *
   * @param value the value
   * @param dataType the data type
   * @return the value
   * @throws Exception the exception
   */
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

  /**
   * Gets the data type.
   *
   * @param data the data
   * @return the data type
   */
  public static Class<?> getDataType(String data) {
    if (StringUtils.isInteger(data) || StringUtils.isDouble(data) || StringUtils.isFloat(data))
      return BigDecimal.class;
    else if (StringUtils.isDateTimestampValid(data)) {
      return Date.class;
    } else {
      return String.class;
    }
  }

  /**
   * Checks if is empty.
   *
   * @param object the object
   * @return true, if is empty
   */
  public static boolean isEmpty(final Object object) {
    if (object == null) {
      return true;
    }
    if (object instanceof CharSequence) {
      return ((CharSequence) object).length() == 0;
    }
    if (object.getClass().isArray()) {
      return Array.getLength(object) == 0;
    }
    if (object instanceof Collection<?>) {
      return ((Collection<?>) object).isEmpty();
    }
    if (object instanceof Map<?, ?>) {
      return ((Map<?, ?>) object).isEmpty();
    }
    return false;
  }

  /**
   * Checks if is not empty.
   *
   * @param object the object
   * @return true, if is not empty
   */
  public static boolean isNotEmpty(final Object object) {
    return !isEmpty(object);
  }

  /**
   * Default if null.
   *
   * @param <T> the generic type
   * @param object the object
   * @param defaultValue the default value
   * @return the t
   */
  public static <T> T defaultIfNull(final T object, final T defaultValue) {
    return object != null ? object : defaultValue;
  }

  /**
   * Clone.
   *
   * @param <T> the generic type
   * @param obj the obj
   * @return the t
   */
  public static <T> T clone(final T obj) {
    if (obj instanceof Cloneable) {
      final Object result;
      if (obj.getClass().isArray()) {
        final Class<?> componentType = obj.getClass().getComponentType();
        if (componentType.isPrimitive()) {
          int length = Array.getLength(obj);
          result = Array.newInstance(componentType, length);
          while (length-- > 0) {
            Array.set(result, length, Array.get(obj, length));
          }
        } else {
          result = ((Object[]) obj).clone();
        }
      } else {
        try {
          final Method clone = obj.getClass().getMethod("clone");
          result = clone.invoke(obj);
        } catch (final NoSuchMethodException e) {
          throw new CloneFailedException(
              "Cloneable type " + obj.getClass().getName() + " has no clone method", e);
        } catch (final IllegalAccessException e) {
          throw new CloneFailedException(
              "Cannot clone Cloneable type " + obj.getClass().getName(), e);
        } catch (final InvocationTargetException e) {
          throw new CloneFailedException(
              "Exception cloning Cloneable type " + obj.getClass().getName(), e.getCause());
        }
      }
      @SuppressWarnings("unchecked") // OK because input is of type T
      final T checked = (T) result;
      return checked;
    }

    return null;
  }
}
