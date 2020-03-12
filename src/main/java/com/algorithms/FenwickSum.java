package com.algorithms;

// TODO: Auto-generated Javadoc
/** The Class FenwickSum. */
public class FenwickSum {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {}

  /**
   * Sum fenwick.
   *
   * @param ft the ft
   * @param i the i
   * @return the long
   */
  public static long sumFenwick(long[] ft, int i) {
    long sum = 0;
    for (i++; i > 0; i -= i & -i) sum += ft[i];
    return sum;
  }
}
