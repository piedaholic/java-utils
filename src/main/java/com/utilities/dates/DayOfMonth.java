package com.utilities.dates;

// TODO: Auto-generated Javadoc
/** The Class DayOfMonth. */
public class DayOfMonth {

  /** The month list. */
  DayOfMonth.Month[] monthList = new DayOfMonth.Month[13];

  /** Instantiates a new day of month. */
  public DayOfMonth() {
    this.monthList[0] = null;
    this.monthList[1] = new DayOfMonth.Month(31, "January");
    this.monthList[2] = new DayOfMonth.Month(28, "February");
    this.monthList[3] = new DayOfMonth.Month(31, "March");
    this.monthList[4] = new DayOfMonth.Month(30, "April");
    this.monthList[5] = new DayOfMonth.Month(31, "May");
    this.monthList[6] = new DayOfMonth.Month(30, "June");
    this.monthList[7] = new DayOfMonth.Month(31, "July");
    this.monthList[8] = new DayOfMonth.Month(31, "August");
    this.monthList[9] = new DayOfMonth.Month(30, "September");
    this.monthList[10] = new DayOfMonth.Month(31, "October");
    this.monthList[11] = new DayOfMonth.Month(30, "November");
    this.monthList[12] = new DayOfMonth.Month(31, "December");
  }

  /**
   * Gets the days for month.
   *
   * @param inYear the in year
   * @param inMonth the in month
   * @return the days for month
   */
  public int getDaysForMonth(int inYear, int inMonth) {
    int daysForMonth;
    boolean leapYearFlag = false;
    leapYearFlag = DateUtils.isLeapYear(inYear);
    daysForMonth = this.monthList[inMonth].getDays();
    if (leapYearFlag && inMonth == 2) {
      ++daysForMonth;
    }

    return daysForMonth;
  }

  /** The Class Month. */
  private class Month {

    /** The days in month. */
    int daysInMonth;

    /**
     * Instantiates a new month.
     *
     * @param inDaysInMonth the in days in month
     * @param inMonthName the in month name
     */
    public Month(int inDaysInMonth, String inMonthName) {
      this.daysInMonth = inDaysInMonth;
    }

    /**
     * Gets the days.
     *
     * @return the days
     */
    public int getDays() {
      return this.daysInMonth;
    }
  }
}
