package com.utilities.object;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

// TODO: Auto-generated Javadoc
/** The Class ClassUtils. */
public class ClassUtils {

  /** Instantiates a new class utils. */
  public ClassUtils() {
    super();
  }

  /**
   * Does method exist.
   *
   * @param object the object
   * @param methodName the method name
   * @return true, if successful
   */
  public static boolean doesMethodExist(final Object object, final String methodName) {
    Method[] methods = object.getClass().getMethods();
    for (Method method : methods) {
      if (method.getName() == methodName || method.getName().equals(methodName)) return true;
    }
    return false;
  }

  /**
   * Does method exist.
   *
   * @param object the object
   * @param methodName the method name
   * @param parameterTypes the parameter types
   * @return true, if successful
   */
  public static boolean doesMethodExist(
      final Object object, final String methodName, final Class<?>... parameterTypes) {
    boolean result = false;
    try {
      object.getClass().getMethod(methodName, parameterTypes);
      result = true;
    } catch (NoSuchMethodException e) {
    }
    return result;
  }

  /**
   * Gets the all interfaces.
   *
   * @param cls the cls
   * @return the all interfaces
   */
  public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
    if (cls == null) {
      return null;
    }

    final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
    getAllInterfaces(cls, interfacesFound);

    return new ArrayList<>(interfacesFound);
  }

  /**
   * Gets the all interfaces.
   *
   * @param cls the cls
   * @param interfacesFound the interfaces found
   * @return the all interfaces
   */
  private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
    while (cls != null) {
      final Class<?>[] interfaces = cls.getInterfaces();

      for (final Class<?> i : interfaces) {
        if (interfacesFound.add(i)) {
          getAllInterfaces(i, interfacesFound);
        }
      }

      cls = cls.getSuperclass();
    }
  }

  /**
   * Gets the all superclasses.
   *
   * @param cls the cls
   * @return the all superclasses
   */
  public static List<Class<?>> getAllSuperclasses(final Class<?> cls) {
    if (cls == null) {
      return null;
    }
    final List<Class<?>> classes = new ArrayList<>();
    Class<?> superclass = cls.getSuperclass();
    while (superclass != null) {
      classes.add(superclass);
      superclass = superclass.getSuperclass();
    }
    return classes;
  }

  /**
   * Gets the public method.
   *
   * @param cls the cls
   * @param methodName the method name
   * @param parameterTypes the parameter types
   * @return the public method
   * @throws NoSuchMethodException the no such method exception
   */
  public static Method getPublicMethod(
      final Class<?> cls, final String methodName, final Class<?>... parameterTypes)
      throws NoSuchMethodException {

    final Method declaredMethod = cls.getMethod(methodName, parameterTypes);
    if (Modifier.isPublic(declaredMethod.getDeclaringClass().getModifiers())) {
      return declaredMethod;
    }

    final List<Class<?>> candidateClasses = new ArrayList<>();
    candidateClasses.addAll(getAllInterfaces(cls));
    candidateClasses.addAll(getAllSuperclasses(cls));

    for (final Class<?> candidateClass : candidateClasses) {
      if (!Modifier.isPublic(candidateClass.getModifiers())) {
        continue;
      }
      Method candidateMethod;
      try {
        candidateMethod = candidateClass.getMethod(methodName, parameterTypes);
      } catch (final NoSuchMethodException ex) {
        continue;
      }
      if (Modifier.isPublic(candidateMethod.getDeclaringClass().getModifiers())) {
        return candidateMethod;
      }
    }

    throw new NoSuchMethodException("Can't find a public method for " + methodName + " ");
  }
}
