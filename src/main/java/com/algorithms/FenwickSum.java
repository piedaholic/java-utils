package com.algorithms;

public class FenwickSum {

    public static void main(String[] args) {
    }

    public static long sumFenwick(long[] ft, int i) {
	long sum = 0;
	for (i++; i > 0; i -= i & -i)
	    sum += ft[i];
	return sum;
    }
}
