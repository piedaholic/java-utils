package com.utilities.dates;

import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/** The Class GregorianDateMatcher. */
public class GregorianDateMatcher {

  /** The date pattern. */
  private static Pattern DATE_PATTERN =
      Pattern.compile(
          "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
              + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
              + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
              + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");

  /** The timestamp pattern. */
  private static Pattern TIMESTAMP_PATTERN =
      Pattern.compile("(00|0[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])$");

  /** The date pattern. */
  public static String datePattern =
      "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
          + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
          + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
          + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";

  /** The timestamp pattern. */
  public static String timestampPattern =
      "(00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])$";

  /**
   * Date matches.
   *
   * @param date the date
   * @return true, if successful
   */
  public static boolean dateMatches(String date) {
    return DATE_PATTERN.matcher(date).matches();
    // return Pattern.compile(datePattern).matcher(date).matches();
  }

  /**
   * Ts matches.
   *
   * @param timestamp the timestamp
   * @return true, if successful
   */
  public static boolean tsMatches(String timestamp) {
    return TIMESTAMP_PATTERN.matcher(timestamp).matches();
    // return Pattern.compile(timestampPattern).matcher(timestamp).matches();
  }
}
