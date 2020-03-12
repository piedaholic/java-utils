package com.utilities.dates;

// TODO: Auto-generated Javadoc
/** The Class DateDifference. */
public class DateDifference {

  /** The total days. */
  private int totalDays = 0;

  /** The year one. */
  private int yearOne = 0;

  /** The month one. */
  private int monthOne = 0;

  /** The day one. */
  private int dayOne = 0;

  /** The year two. */
  private int yearTwo = 0;

  /** The month two. */
  private int monthTwo = 0;

  /** The day two. */
  private int dayTwo = 0;

  /** The day of month. */
  private DayOfMonth dayOfMonth = new DayOfMonth();

  /** The low year. */
  private int lowYear = 1901;

  /** The high year. */
  private int highYear = 9000;

  /** Instantiates a new date difference. */
  public DateDifference() {}

  /**
   * Instantiates a new date difference.
   *
   * @param year1 the year 1
   * @param month1 the month 1
   * @param day1 the day 1
   * @param year2 the year 2
   * @param month2 the month 2
   * @param day2 the day 2
   * @throws IllegalArgumentException the illegal argument exception
   */
  public DateDifference(int year1, int month1, int day1, int year2, int month2, int day2)
      throws IllegalArgumentException {
    boolean errorFlag = false;
    this.yearOne = year1;
    this.monthOne = month1;
    this.dayOne = day1;
    this.yearTwo = year2;
    this.monthTwo = month2;
    this.dayTwo = day2;
    errorFlag = false;
    if (this.yearOne >= this.lowYear && this.yearOne <= this.highYear) {
      if (this.yearTwo >= this.lowYear && this.yearTwo <= this.highYear) {
        if (this.monthOne >= 1 && this.monthOne <= 12) {
          if (this.monthTwo >= 1 && this.monthTwo <= 12) {
            if (this.dayOne >= 1
                && this.dayOne <= this.dayOfMonth.getDaysForMonth(this.yearOne, this.monthOne)) {
              if (this.dayTwo < 1
                  || this.dayTwo > this.dayOfMonth.getDaysForMonth(this.yearTwo, this.monthTwo)) {
                errorFlag = true;
              }
            } else {
              errorFlag = true;
            }
          } else {
            errorFlag = true;
          }
        } else {
          errorFlag = true;
        }
      } else {
        errorFlag = true;
      }
    } else {
      errorFlag = true;
    }

    if (errorFlag) {
      throw new IllegalArgumentException("Bad value in one of the date parameters.");
    }
  }

  /**
   * Gets the day difference.
   *
   * @return the day difference
   */
  public int getDayDifference() {
    int yearDiffCount;
    int monthDiffCount;
    int tempDays;
    this.totalDays = 0;
    this.sortDates();
    if (this.yearOne < this.yearTwo) {
      yearDiffCount = this.yearTwo - this.yearOne;
      if (yearDiffCount >= 2) {
        this.totalDays = this.getYearDiff(this.yearOne, this.yearTwo);
      }
    }

    if (this.yearOne == this.yearTwo) {
      if (this.monthOne == this.monthTwo) {
        tempDays = this.dayTwo - this.dayOne - 1;
        if (tempDays < 0) {
          tempDays = 0;
        }

        this.totalDays += tempDays;
      } else {
        this.totalDays =
            this.totalDays
                + this.dayOfMonth.getDaysForMonth(this.yearOne, this.monthOne)
                - this.dayOne;
        monthDiffCount = this.monthTwo - this.monthOne;
        if (monthDiffCount >= 2) {
          this.totalDays += this.getMonthDiff(this.yearOne, this.monthOne + 1, this.monthTwo - 1);
        }

        this.totalDays = this.totalDays + this.dayTwo - 1;
      }
    } else {
      this.totalDays =
          this.totalDays
              + this.dayOfMonth.getDaysForMonth(this.yearOne, this.monthOne)
              - this.dayOne;
      monthDiffCount = 13 - this.monthOne;
      if (monthDiffCount >= 2) {
        this.totalDays += this.getMonthDiff(this.yearOne, this.monthOne + 1, 12);
      }

      if (this.monthTwo > 1) {
        this.totalDays += this.getMonthDiff(this.yearTwo, 1, this.monthTwo - 1);
      }

      this.totalDays = this.totalDays + this.dayTwo - 1;
    }

    return this.totalDays;
  }

  /**
   * Gets the month diff.
   *
   * @param inCurrYear the in curr year
   * @param lowMonth the low month
   * @param highMonth the high month
   * @return the month diff
   */
  private int getMonthDiff(int inCurrYear, int lowMonth, int highMonth) {
    int x;
    int daysInMonth;
    int totDays = 0;

    for (x = lowMonth; x <= highMonth; ++x) {
      daysInMonth = this.dayOfMonth.getDaysForMonth(inCurrYear, x);
      totDays += daysInMonth;
    }

    return totDays;
  }

  /**
   * Gets the year diff.
   *
   * @param lowYear the low year
   * @param highYear the high year
   * @return the year diff
   */
  private int getYearDiff(int lowYear, int highYear) {
    int x;
    int daysInYear;
    int totDays = 0;

    for (x = lowYear + 1; x <= highYear - 1; ++x) {
      if (DateUtils.isLeapYear(x)) {
        daysInYear = 366;
      } else {
        daysInYear = 365;
      }

      totDays += daysInYear;
    }

    return totDays;
  }

  /** Sort dates. */
  private void sortDates() {
    if (this.yearOne > this.yearTwo) {
      this.swapDates("Y");
    } else if (this.yearOne == this.yearTwo && this.monthOne > this.monthTwo) {
      this.swapDates("M");
    } else if (this.yearOne == this.yearTwo
        && this.monthOne == this.monthTwo
        && this.dayOne > this.dayTwo) {
      this.swapDates("D");
    }
  }

  /**
   * Swap dates.
   *
   * @param swapScope the swap scope
   */
  private void swapDates(String swapScope) {
    int tempYear = this.yearOne;
    int tempMonth = this.monthOne;
    int tempDay = this.dayOne;
    if (swapScope.equals("Y")) {
      this.yearOne = this.yearTwo;
      this.yearTwo = tempYear;
      this.monthOne = this.monthTwo;
      this.monthTwo = tempMonth;
      this.dayOne = this.dayTwo;
      this.dayTwo = tempDay;
    } else if (swapScope.equals("M")) {
      this.monthOne = this.monthTwo;
      this.monthTwo = tempMonth;
      this.dayOne = this.dayTwo;
      this.dayTwo = tempDay;
    } else if (swapScope.equals("D")) {
      this.dayOne = this.dayTwo;
      this.dayTwo = tempDay;
    }
  }
}
