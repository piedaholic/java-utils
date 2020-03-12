package com.utilities.design;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

// TODO: Auto-generated Javadoc
/** The Class UtilMethods. */
public class UtilMethods {

  /** The id counter. */
  private static AtomicLong idCounter = new AtomicLong();

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    ParentClass parentClass = new ParentClass();
    EmbeddableClass embeddableClass = new EmbeddableClass();
    parentClass.setEmbeddableClass(embeddableClass);
    // getClassFields(parentClass);
    System.out.println(getInterfaceName(embeddableClass));
    Class<?> clazz = Long.class;
    Class<?> superClazz = clazz;
    System.out.println(superClazz.getSuperclass().getName());
    System.out.println(superClazz.getSuperclass().getName());
    String uniqueID = UUID.randomUUID().toString();
    System.out.println(uniqueID);
    System.out.println(String.valueOf(idCounter.getAndIncrement()));
    ChildClass childClass = new ChildClass();
    childClass.setId("1");
    System.out.println(childClass.getId());
    childClass.setId("12");
    System.out.println(childClass.getId());
    childClass.setId("123");
    System.out.println(childClass.getId());
    if (childClass instanceof RootInterface) System.out.println("Yes");
    else {
      System.out.println("No");
    }

    if (childClass instanceof ParentClass) System.out.println("Yes");
    else {
      System.out.println("No");
    }
  }

  /**
   * Gets the fields up to.
   *
   * @param startClass the start class
   * @param exclusiveParent the exclusive parent
   * @return the fields up to
   */
  public static Iterable<Field> getFieldsUpTo(Class<?> startClass, Class<?> exclusiveParent) {

    List<Field> currentClassFields = Arrays.asList(startClass.getDeclaredFields());
    Class<?> parentClass = startClass.getSuperclass();

    if (parentClass != null
        && (exclusiveParent == null || !(parentClass.equals(exclusiveParent)))) {
      List<Field> parentClassFields = (List<Field>) getFieldsUpTo(parentClass, exclusiveParent);
      currentClassFields.addAll(parentClassFields);
    }

    return currentClassFields;
  }

  /*
   * public static void getClassFields(Object obj) { List<Field> currentClassFields =
   * Arrays.asList(obj.getClass().getDeclaredFields()); for (Iterator<Field> iterator =
   * currentClassFields.iterator(); iterator.hasNext();) { Field field = (Field) iterator.next();
   * System.out.println(RootInterface.class.isInstance(field.getType().getClass()));
   * System.out.println(RootInterface.class.isAssignableFrom(field.getType()));
   * System.out.println(field.getName()); } }
   */
  /**
   * Check field.
   *
   * @param field the field
   * @return true, if successful
   */
  public static boolean checkField(Field field) {
    if (Collection.class.isAssignableFrom(field.getType())) {
      return true;
    }
    return false;
  }

  /**
   * Gets the field.
   *
   * @param fldName the fld name
   * @param obj the obj
   * @return the field
   */
  public Field getField(String fldName, Object obj) {
    Class<?> someClass = obj.getClass();
    Field someField = null;
    try {
      someField = someClass.getField(fldName);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    return someField;
  }

  /**
   * Gets the interface name.
   *
   * @param fld the fld
   * @return the interface name
   */
  public static String getInterfaceName(Object fld) {
    String result = "";
    Class<?>[] arr = fld.getClass().getInterfaces();
    for (int i = 0; i < arr.length; i++) {
      result = result + arr[i].getSimpleName() + "~";
    }
    return result;
  }
}
