package com.utilities.object;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static BigDecimal getValueAsBigDecimal(String value) throws NumberFormatException {
	return value == null ? null : new BigDecimal(value);
    }

    public static char getValueAsChar(String value) {
	return value == null ? null : value.charAt(0);
    }

    public static Character getValueAsCharacter(boolean value) {
	return new Character(value ? 'Y' : 'N');
    }

    public static Character getValueAsCharacter(char value) {
	return new Character(value);
    }

    public static Character getValueAsCharacter(String value) {
	return new Character(value.charAt(0));
    }

    public static Date getValueAsDate(String value) throws ParseException {
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	return value == null ? null : df.parse(value);
    }

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

    public static Date getValueAsTimestamp(String value) throws ParseException {
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	return value == null ? null : df.parse(value);
    }

    public static Date getValueAsDate(String dateString, String dateFormat) throws ParseException {
	DateFormat df = new SimpleDateFormat(dateFormat);

	return dateString == null ? null : df.parse(dateString);
    }

    public static Double getValueAsDouble(String value) throws NumberFormatException {
	return value == null ? null : Double.valueOf(Double.parseDouble(value));
    }

    public static String getValueAsString(BigDecimal bigDecimal) {
	return bigDecimal == null ? null : bigDecimal.toPlainString();
    }

    public static String getValueAsString(Long longValue) {
	return longValue == null ? null : longValue.toString();
    }

    public static String getValueAsString(char value) {
	return new String(new char[] { value });
    }

    public static String getValueAsString(Double value) {
	return value == null ? null : value.toString();
    }

    public static boolean getValueAsBoolean(String value) {
	return value != null;
    }

    public static String getValueAsString(boolean value) {
	return value ? "Y" : "N";
    }

    public static boolean getValueAsBoolean(Character value) {
	return value.charValue() == 'Y';
    }

    public static boolean getValueAsBoolean(char value) {
	return value == 'Y';
    }

    public static Integer getValueAsInteger(String value) {
	return new Integer(value);
    }

    public static long getValueAsLong(String value) {
	return value == null ? 0L : Long.valueOf(value).longValue();
    }

    public static long getValueAsLong(BigDecimal value) {
	return value == null ? 0L : value.longValue();
    }

    public static int getValueAsInteger(BigDecimal value) {
	return value == null ? 0 : value.intValue();
    }

    public static BigDecimal getValueAsBigDecimal(long value) {
	return BigDecimal.valueOf(value);
    }

    public static String getValueAsString(long value) {
	return String.valueOf(value);
    }

    public static String getValueAsString(int value) {
	return String.valueOf(value);
    }

    public static int getValueAsInt(String value) {
	return value == null ? 0 : new Integer(value).intValue();
    }

    public static String dateToString(Date date) {
	if (date != null) {
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String stringDate = formatter.format(date);
	    return stringDate;
	}
	return null;
    }

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
