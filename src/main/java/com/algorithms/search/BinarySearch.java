package com.algorithms.search;

import java.util.Arrays;
import com.utilities.object.ObjectUtils;

public class BinarySearch implements Search {

  public int search(byte[] byteArr, byte key) {
    if (ObjectUtils.isNull(byteArr)) {
      Arrays.sort(byteArr);
      return Arrays.binarySearch(byteArr, key);
    }
    return -1;
  }

  public int search(char[] charArr, char key) {
    if (ObjectUtils.isNull(charArr)) {
      Arrays.sort(charArr);
      return Arrays.binarySearch(charArr, key);
    }
    return -1;
  }

  public int search(int[] intArr, int key) {
    if (ObjectUtils.isNull(intArr)) {
      Arrays.sort(intArr);
      return Arrays.binarySearch(intArr, key);
    }
    return -1;
  }

  public int search(double[] doubleArr, double key) {
    if (ObjectUtils.isNull(doubleArr)) {
      Arrays.sort(doubleArr);
      return Arrays.binarySearch(doubleArr, key);
    }
    return -1;
  }

  public int search(float[] floatArr, float key) {
    if (ObjectUtils.isNull(floatArr)) {
      Arrays.sort(floatArr);
      return Arrays.binarySearch(floatArr, key);
    }
    return -1;
  }

  public int search(short[] shortArr, short key) {
    if (ObjectUtils.isNull(shortArr)) {
      Arrays.sort(shortArr);
      return Arrays.binarySearch(shortArr, key);
    }
    return -1;
  }

}
