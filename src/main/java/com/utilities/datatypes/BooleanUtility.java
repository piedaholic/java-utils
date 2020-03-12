package com.utilities.datatypes;

// TODO: Auto-generated Javadoc
/** The Class BooleanUtility. */
public class BooleanUtility {

  /**
   * Gets the boolean.
   *
   * @param inString the in string
   * @return the boolean
   */
  public static boolean getBoolean(String inString) {
    if (inString != null) {
      return inString.equalsIgnoreCase("Y");
    } else {
      return false;
    }
  }

  /**
   * Gets the string.
   *
   * @param flag the flag
   * @return the string
   */
  public static String getString(boolean flag) {
    return flag ? "Y" : "N";
  }
}
