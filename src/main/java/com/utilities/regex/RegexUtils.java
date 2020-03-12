/** */
package com.utilities.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class RegexUtils.
 *
 * @author hpsingh
 */
public class RegexUtils {

  /**
   * Matches.
   *
   * @param patternString the pattern string
   * @param text the text
   * @return true, if successful
   */
  public static boolean matches(String patternString, String text) {
    boolean result = true;
    Pattern pattern = Pattern.compile(patternString);
    Matcher matcher = pattern.matcher(text);
    result = matcher.matches();
    return result;
  }
}
