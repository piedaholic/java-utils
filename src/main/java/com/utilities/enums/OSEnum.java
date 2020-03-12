/** */
package com.utilities.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum OSEnum.
 *
 * @author hpsingh
 */
public enum OSEnum {

  /** The windows. */
  WINDOWS,
  /** The linux. */
  LINUX;

  /**
   * From value.
   *
   * @param v the v
   * @return the OS enum
   */
  public static OSEnum fromValue(String v) {
    return valueOf(v);
  }

  /**
   * Contains.
   *
   * @param test the test
   * @return true, if successful
   */
  public static boolean contains(String test) {

    for (OSEnum c : OSEnum.values()) {
      if (c.name().equals(test)) {
        return true;
      }
    }

    return false;
  }
}
