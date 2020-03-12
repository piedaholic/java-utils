package com.utilities.object;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/** The Class ConversionUtils. */
public class ConversionUtils {

  /** The Constant DATE_FORMAT. */
  public static final String DATE_FORMAT = "yyyy-MM-dd";

  /** The Constant TIMESTAMP_FORMAT. */
  public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

  /**
   * Gets the value as big decimal.
   *
   * @param value the value
   * @return the value as big decimal
   * @throws NumberFormatException the number format exception
   */
  public static BigDecimal getValueAsBigDecimal(String value) throws NumberFormatException {
    return value == null ? null : new BigDecimal(value);
  }

  /**
   * Gets the value as char.
   *
   * @param value the value
   * @return the value as char
   */
  public static char getValueAsChar(String value) {
    return value == null ? null : value.charAt(0);
  }

  /**
   * Gets the value as character.
   *
   * @param value the value
   * @return the value as character
   */
  public static Character getValueAsCharacter(boolean value) {
    return new Character(value ? 'Y' : 'N');
  }

  /**
   * Gets the value as character.
   *
   * @param value the value
   * @return the value as character
   */
  public static Character getValueAsCharacter(char value) {
    return new Character(value);
  }

  /**
   * Gets the value as character.
   *
   * @param value the value
   * @return the value as character
   */
  public static Character getValueAsCharacter(String value) {
    return new Character(value.charAt(0));
  }

  /**
   * Gets the value as date.
   *
   * @param value the value
   * @return the value as date
   * @throws ParseException the parse exception
   */
  public static Date getValueAsDate(String value) throws ParseException {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    return value == null ? null : df.parse(value);
  }

  /**
   * Gets the value as date input.
   *
   * @param value the value
   * @return the value as date input
   * @throws ParseException the parse exception
   */
  public static Date getValueAsDateInput(String value) throws ParseException {
    if (value != null) {
      String year = value.substring(0, 4);
      String month = value.substring(4, 6);
      String date = value.substring(6, 8);

      value = year + "-" + month + "-" + date;
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    return value == null ? null : df.parse(value);
  }

  /**
   * Gets the value as timestamp.
   *
   * @param value the value
   * @return the value as timestamp
   * @throws ParseException the parse exception
   */
  public static Date getValueAsTimestamp(String value) throws ParseException {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return value == null ? null : df.parse(value);
  }

  /**
   * Gets the value as date.
   *
   * @param dateString the date string
   * @param dateFormat the date format
   * @return the value as date
   * @throws ParseException the parse exception
   */
  public static Date getValueAsDate(String dateString, String dateFormat) throws ParseException {
    DateFormat df = new SimpleDateFormat(dateFormat);

    return dateString == null ? null : df.parse(dateString);
  }

  /**
   * Gets the value as double.
   *
   * @param value the value
   * @return the value as double
   * @throws NumberFormatException the number format exception
   */
  public static Double getValueAsDouble(String value) throws NumberFormatException {
    return value == null ? null : Double.valueOf(Double.parseDouble(value));
  }

  /**
   * Gets the value as string.
   *
   * @param bigDecimal the big decimal
   * @return the value as string
   */
  public static String getValueAsString(BigDecimal bigDecimal) {
    return bigDecimal == null ? null : bigDecimal.toPlainString();
  }

  /**
   * Gets the value as string.
   *
   * @param longValue the long value
   * @return the value as string
   */
  public static String getValueAsString(Long longValue) {
    return longValue == null ? null : longValue.toString();
  }

  /**
   * Gets the value as string.
   *
   * @param value the value
   * @return the value as string
   */
  public static String getValueAsString(char value) {
    return new String(new char[] {value});
  }

  /**
   * Gets the value as string.
   *
   * @param value the value
   * @return the value as string
   */
  public static String getValueAsString(Double value) {
    return value == null ? null : value.toString();
  }

  /**
   * Gets the value as boolean.
   *
   * @param value the value
   * @return the value as boolean
   */
  public static boolean getValueAsBoolean(String value) {
    return value != null;
  }

  /**
   * Gets the value as string.
   *
   * @param value the value
   * @return the value as string
   */
  public static String getValueAsString(boolean value) {
    return value ? "Y" : "N";
  }

  /**
   * Gets the value as boolean.
   *
   * @param value the value
   * @return the value as boolean
   */
  public static boolean getValueAsBoolean(Character value) {
    return value.charValue() == 'Y';
  }

  /**
   * Gets the value as boolean.
   *
   * @param value the value
   * @return the value as boolean
   */
  public static boolean getValueAsBoolean(char value) {
    return value == 'Y';
  }

  /**
   * Gets the value as integer.
   *
   * @param value the value
   * @return the value as integer
   */
  public static Integer getValueAsInteger(String value) {
    return new Integer(value);
  }

  /**
   * Gets the value as long.
   *
   * @param value the value
   * @return the value as long
   */
  public static long getValueAsLong(String value) {
    return value == null ? 0L : Long.valueOf(value).longValue();
  }

  /**
   * Gets the value as long.
   *
   * @param value the value
   * @return the value as long
   */
  public static long getValueAsLong(BigDecimal value) {
    return value == null ? 0L : value.longValue();
  }

  /**
   * Gets the value as integer.
   *
   * @param value the value
   * @return the value as integer
   */
  public static int getValueAsInteger(BigDecimal value) {
    return value == null ? 0 : value.intValue();
  }

  /**
   * Gets the value as big decimal.
   *
   * @param value the value
   * @return the value as big decimal
   */
  public static BigDecimal getValueAsBigDecimal(long value) {
    return BigDecimal.valueOf(value);
  }

  /**
   * Gets the value as string.
   *
   * @param value the value
   * @return the value as string
   */
  public static String getValueAsString(long value) {
    return String.valueOf(value);
  }

  /**
   * Gets the value as string.
   *
   * @param value the value
   * @return the value as string
   */
  public static String getValueAsString(int value) {
    return String.valueOf(value);
  }

  /**
   * Gets the value as int.
   *
   * @param value the value
   * @return the value as int
   */
  public static int getValueAsInt(String value) {
    return value == null ? 0 : new Integer(value).intValue();
  }

  /**
   * Date to string.
   *
   * @param date the date
   * @return the string
   */
  public static String dateToString(Date date) {
    if (date != null) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String stringDate = formatter.format(date);
      return stringDate;
    }
    return null;
  }

  /**
   * Date to string.
   *
   * @param date the date
   * @param stat the stat
   * @return the string
   */
  public static String dateToString(Date date, Boolean stat) {
    if (date != null) {
      SimpleDateFormat formatter;
      if (stat.booleanValue() == true) {
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      } else {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
      }
      String stringDate = formatter.format(date);

      return stringDate;
    }
    return null;
  }
}
