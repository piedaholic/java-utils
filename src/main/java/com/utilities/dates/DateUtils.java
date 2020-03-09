package com.utilities.dates;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    /*
     * Add Day/Month/Year to a Date add() is used to add values to a Calendar
     * object. You specify which Calendar field is to be affected by the operation
     * (Calendar.YEAR, Calendar.MONTH, Calendar.DATE).
     */
    public static void addToDate() {
	System.out.println("In the ADD Operation");
	// String DATE_FORMAT = "yyyy-MM-dd";
	String DATE_FORMAT = "dd-MM-yyyy"; // Refer Java DOCS for formats
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
	Calendar c1 = Calendar.getInstance();
	Date d1 = new Date();
	System.out.println("Todays date in Calendar Format : " + c1);
	System.out.println("c1.getTime() : " + c1.getTime());
	System.out.println("c1.get(Calendar.YEAR): " + c1.get(Calendar.YEAR));
	System.out.println("Todays date in Date Format : " + d1);
	c1.set(1999, 0, 20); // (year,month,date)
	System.out.println("c1.set(1999,0 ,20) : " + c1.getTime());
	c1.add(Calendar.DATE, 40);
	System.out.println("Date + 20 days is : " + sdf.format(c1.getTime()));
	System.out.println();
	System.out.println();
    }

    /*
     * Substract Day/Month/Year to a Date roll() is used to substract values to a
     * Calendar object. You specify which Calendar field is to be affected by the
     * operation (Calendar.YEAR, Calendar.MONTH, Calendar.DATE).
     * 
     * Note: To substract, simply use a negative argument. roll() does the same
     * thing except you specify if you want to roll up (add 1) or roll down
     * (substract 1) to the specified Calendar field. The operation only affects the
     * specified field while add() adjusts other Calendar fields. See the following
     * example, roll() makes january rolls to december in the same year while add()
     * substract the YEAR field for the correct result
     * 
     */

    public static void subToDate() {
	System.out.println("In the SUB Operation");
	String DATE_FORMAT = "dd-MM-yyyy";
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
	Calendar c1 = Calendar.getInstance();
	c1.set(1999, 0, 20);
	System.out.println("Date is : " + sdf.format(c1.getTime()));

	// roll down, substract 1 month
	c1.roll(Calendar.MONTH, false);
	System.out.println("Date roll down 1 month : " + sdf.format(c1.getTime()));

	c1.set(1999, 0, 20);
	System.out.println("Date is : " + sdf.format(c1.getTime()));
	c1.add(Calendar.MONTH, -1);
	// substract 1 month
	System.out.println("Date minus 1 month : " + sdf.format(c1.getTime()));
	System.out.println();
	System.out.println();
    }

    public static void daysBetween2Dates() {
	Calendar c1 = Calendar.getInstance(); // new GregorianCalendar();
	Calendar c2 = Calendar.getInstance(); // new GregorianCalendar();
	c1.set(1999, 0, 20);
	c2.set(1999, 0, 22);
	System.out.println("Days Between " + c1.getTime() + "\t" + c2.getTime() + " is");
	System.out.println((c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000));
	System.out.println();
	System.out.println();
    }

    public static void daysInMonth() {
	Calendar c1 = Calendar.getInstance(); // new GregorianCalendar();
	c1.set(1999, 6, 20);
	int year = c1.get(Calendar.YEAR);
	int month = c1.get(Calendar.MONTH);
	// int days = c1.get(Calendar.DATE);
	int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	daysInMonths[1] += DateUtils.isLeapYear(year) ? 1 : 0;
	System.out.println(
		"Days in " + month + "th month for year " + year + " is " + daysInMonths[c1.get(Calendar.MONTH)]);
	System.out.println();
	System.out.println();
    }

    public static void getDayofTheDate() {
	Date d1 = new Date();
	String day = null;
	DateFormat f = new SimpleDateFormat("EEEE");
	try {
	    day = f.format(d1);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	System.out.println("The dat for " + d1 + " is " + day);
	System.out.println();
	System.out.println();
    }

    public static void validateAGivenDate() {
	String dt = "20011223";
	String invalidDt = "20031315";
	String dateformat = "yyyyMMdd";
	Date dt1 = null;
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
	    sdf.setLenient(false);
	    dt1 = sdf.parse(dt);
	    sdf.parse(invalidDt);
	    System.out.println("Date is ok = " + dt1 + "(" + dt + ")");
	} catch (ParseException e) {
	    System.out.println(e.getMessage());
	} catch (IllegalArgumentException e) {
	    System.out.println("Invalid date");
	}
	System.out.println();
	System.out.println();
    }

    public static void compare2Dates() {
	SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
	Calendar c1 = Calendar.getInstance();
	Calendar c2 = Calendar.getInstance();

	c1.set(2000, 02, 15);
	c2.set(2001, 02, 15);

	System.out.print(fm.format(c1.getTime()) + " is ");
	if (c1.before(c2)) {
	    System.out.println("less than " + c2.getTime());
	} else if (c1.after(c2)) {
	    System.out.println("greater than " + c2.getTime());
	} else if (c1.equals(c2)) {
	    System.out.println("is equal to " + fm.format(c2.getTime()));
	}
	System.out.println();
	System.out.println();
    }

    public static boolean isLeapYear(int year) {
	if ((year % 100 != 0) || (year % 400 == 0)) {
	    return true;
	}
	return false;
    }

    public static void main(String args[]) {
	addToDate();
	subToDate();
	daysBetween2Dates(); // The "right" way would be to compute the julian day number of both dates and
			     // then do the substraction.
	daysInMonth();
	validateAGivenDate();
	compare2Dates();
	getDayofTheDate();
    }

    private static final SimpleDateFormat PLSQL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static java.sql.Date addDays(Date date, int noOfDays) {
	int year;
	int month;
	int day;
	java.sql.Date dt = null;
	if (date != null) {
	    String str = date.toString();
	    year = Integer.parseInt(str.substring(0, 4));
	    month = Integer.parseInt(str.substring(5, 7)) - 1;
	    day = Integer.parseInt(str.substring(8, 10));
	    Calendar calendar = new GregorianCalendar(year, month, day);
	    calendar.add(5, noOfDays);
	    (new StringBuilder()).append(calendar.get(1)).append("-").append(calendar.get(2) + 1).append("-")
		    .append(calendar.get(5)).toString();
	    String strdate = formDateInString(calendar.get(5), calendar.get(2) + 1, calendar.get(1));
	    dt = java.sql.Date.valueOf(strdate);
	}

	return dt;
    }

    public static Date subDays(Date date, int noOfDays) {
	int year;
	int month;
	int day;
	Date dt = null;
	if (date != null) {
	    String str = date.toString();
	    year = Integer.parseInt(str.substring(0, 4));
	    month = Integer.parseInt(str.substring(5, 7)) - 1;
	    day = Integer.parseInt(str.substring(8, 10));
	    Calendar calendar = new GregorianCalendar(year, month, day);
	    calendar.add(5, -noOfDays);
	    (new StringBuilder()).append(calendar.get(1)).append("-").append(calendar.get(2) + 1).append("-")
		    .append(calendar.get(5)).toString();
	    String strdate = formDateInString(calendar.get(5), calendar.get(2) + 1, calendar.get(1));
	    dt = java.sql.Date.valueOf(strdate);
	}

	return dt;
    }

    public static java.sql.Date addMonths(Date date, int noOfMonths) {
	int year;
	int month;
	int day;
	java.sql.Date dt = null;
	if (date != null) {
	    String str = date.toString();
	    year = Integer.parseInt(str.substring(0, 4));
	    month = Integer.parseInt(str.substring(5, 7)) - 1;
	    day = Integer.parseInt(str.substring(8, 10));
	    Calendar calendar = new GregorianCalendar(year, month, day);
	    calendar.add(2, noOfMonths);
	    String strdate = calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5);
	    dt = java.sql.Date.valueOf(strdate);
	}

	return dt;
    }

    public static java.sql.Date addYears(Date date, int noOfYears) {
	int year;
	int month;
	int day;
	java.sql.Date dt = null;
	if (date != null) {
	    String str = date.toString();
	    year = Integer.parseInt(str.substring(0, 4));
	    month = Integer.parseInt(str.substring(5, 7)) - 1;
	    day = Integer.parseInt(str.substring(8, 10));
	    Calendar calendar = new GregorianCalendar(year, month, day);
	    calendar.add(1, noOfYears);
	    String strdate = calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5);
	    dt = java.sql.Date.valueOf(strdate);
	}

	return dt;
    }

    public static int findDiff(Date date1, Date date2) {
	int noOfDays;
	int year1;
	int month1;
	int day1;
	int year2;
	int month2;
	int day2;
	String str = "";
	if (date1 != null) {
	    str = date1.toString();
	    year1 = Integer.parseInt(str.substring(0, 4));
	    month1 = Integer.parseInt(str.substring(5, 7));
	    day1 = Integer.parseInt(str.substring(8, 10));
	    if (date2 != null) {
		str = date2.toString();
		year2 = Integer.parseInt(str.substring(0, 4));
		month2 = Integer.parseInt(str.substring(5, 7));
		day2 = Integer.parseInt(str.substring(8, 10));
		DateDifference var10 = new DateDifference(year1, month1, day1, year2, month2, day2);
		noOfDays = var10.getDayDifference();
		++noOfDays;
		return noOfDays;
	    } else {
		return 0;
	    }
	} else {
	    return 0;
	}
    }

    public static String getYYDDD(Date date) {
	int year;
	int month;
	int day;
	if (date != null) {
	    String str = date.toString();
	    year = Integer.parseInt(str.substring(0, 4));
	    month = Integer.parseInt(str.substring(5, 7)) - 1;
	    day = Integer.parseInt(str.substring(8, 10));
	    Calendar calendar = new GregorianCalendar(year, month, day);
	    return str.substring(2, 4) + calendar.get(6) + "";
	} else {
	    return "";
	}
    }

    /*
     * public static boolean isLeapYear(int inYear) { boolean leapFlag = false; if
     * (inYear % 4 == 0) { if (inYear % 100 == 0) { if (inYear % 400 == 0) {
     * leapFlag = true; } else { leapFlag = false; } } else { leapFlag = true; } }
     * else { leapFlag = false; }
     * 
     * return leapFlag; }
     */

    public static int daysInMonth(int pMonth, int pYear) {
	if (pMonth != 4 && pMonth != 6 && pMonth != 9 && pMonth != 11) {
	    if (pMonth == 2) {
		return isLeapYear(pYear) ? 29 : 28;
	    } else {
		return 31;
	    }
	} else {
	    return 30;
	}
    }

    public static int daysInYear(int pYear) {
	return isLeapYear(pYear) ? 366 : 365;
    }

    public static int getDay(Date pDate) {
	int day = 0;
	if (pDate != null) {
	    String str = pDate.toString();
	    day = Integer.parseInt(str.substring(8, 10));
	}

	return day;
    }

    public static int getDay(String pDate) {
	int day = 0;
	if (pDate != null && pDate.length() != 0) {
	    String str = pDate.toString();
	    day = Integer.parseInt(str.substring(8, 10));
	}

	return day;
    }

    public static int getMonth(Date pDate) {
	int month = 0;
	if (pDate != null) {
	    String str = pDate.toString();
	    month = Integer.parseInt(str.substring(5, 7));
	}

	return month;
    }

    public static int getMonth(String pDate) {
	int month = 0;
	if (pDate != null && pDate.length() != 0) {
	    String str = pDate.toString();
	    month = Integer.parseInt(str.substring(5, 7));
	}

	return month;
    }

    public static int getYear(Date pDate) {
	int year = 0;
	if (pDate != null) {
	    String str = pDate.toString();
	    year = Integer.parseInt(str.substring(0, 4));
	}

	return year;
    }

    public static int getYear(String pDate) {
	int year = 0;
	if (pDate != null && pDate.length() != 0) {
	    String str = pDate.toString();
	    year = Integer.parseInt(str.substring(0, 4));
	}

	return year;
    }

    public static Date format(String pDateToFormat, String pFrom) {
	return java.sql.Date.valueOf(format(pDateToFormat, pFrom, "YYYY-MM-DD"));
    }

    public static String format(String pDateToFormat, String pFrom, String pTo) {
	String strFormattedDate = "";
	return strFormattedDate;
    }

    public static String formDateInString(int pDay, int pMonth, int pYear) {
	String strDay = String.valueOf(pDay);
	String strMonth = String.valueOf(pMonth);
	if (strDay.length() == 1) {
	    strDay = "0" + strDay;
	}

	if (strMonth.length() == 1) {
	    strMonth = "0" + strMonth;
	}

	return pYear + "-" + strMonth + "-" + strDay;
    }

    public static java.sql.Date formDate(int pDay, int pMonth, int pYear) {
	return java.sql.Date.valueOf(formDateInString(pDay, pMonth, pYear));
    }

    public static boolean isValidDate(String pDateStr) {
	if (pDateStr.length() >= 10) {
	    String date = pDateStr.substring(0, 10);
	    String[] strArr = date.split("-");
	    int year = Integer.parseInt(strArr[0]);
	    int month = Integer.parseInt(strArr[1]);
	    int dayOfMonth = Integer.parseInt(strArr[2]);
	    if (dayOfMonth > 0 && dayOfMonth <= 31) {
		if (month > 0 && month <= 12) {
		    if (year <= 0) {
			return false;
		    } else if (month != 1 && month != 3 && month != 5 && month != 7 && month != 8 && month != 10
			    && month != 12) {
			if (month == 2) {
			    if (!isLeapYear(year) && dayOfMonth > 28) {
				return false;
			    }

			    if (dayOfMonth > 29) {
				return false;
			    }
			} else if (dayOfMonth > 30) {
			    return false;
			}

			return true;
		    } else {
			return true;
		    }
		} else {
		    return false;
		}
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }

    public static boolean isLastDayOfMonth(Date date) {
	boolean lastDayMonth = false;
	int month = getMonth(date);
	int day = getDay(date);
	int year = getYear(date);
	int lastDay = daysInMonth(month, year);
	if (lastDay == day) {
	    lastDayMonth = true;
	}

	return lastDayMonth;
    }

    public static String getZoneDateTime(String strTargetZone) {

	java.util.Date date = convertToTimeZone(new java.util.Date(), ZoneId.systemDefault().getId(), strTargetZone);
	return PLSQL_FORMAT.format(date);
    }

    public static java.util.Date getUtilZoneDateTime(String strTargetZone) {
	return convertToTimeZone(new java.util.Date(), ZoneId.systemDefault().getId(), strTargetZone);
    }

    public static java.util.Date getUtilZoneDateTime(String strTargetZone, java.util.Date dt) {
	return convertToTimeZone(dt, ZoneId.systemDefault().getId(), strTargetZone);
    }

    public static java.util.Date getUtilSystemDateTime(String strSourceZone, java.util.Date zoneDate) {
	return convertToTimeZone(zoneDate, strSourceZone, ZoneId.systemDefault().toString());
    }

    public static java.util.Date convertToTimeZone(java.util.Date date, String tzFrom, String tzTo) {
	Calendar cal = Calendar.getInstance();
	java.util.Date convertedDate = java.util.Date
		.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(tzTo)).atZone(ZoneId.of(tzFrom)).toInstant());
	cal.setTime(convertedDate);
	cal.setTimeZone(TimeZone.getTimeZone(ZoneId.of(tzTo)));
	return cal.getTime();
    }

    public static String parseDate(Date date, String format) {

	// sdf.applyPattern();
	// sdf.format(date);
	String fxpo = "";
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat();
	    sdf.applyPattern(format);
	    fxpo = sdf.format(date);
	    System.out.println(fxpo);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	return fxpo;
    }

}