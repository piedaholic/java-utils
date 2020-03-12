package com.datastructures.array;

import com.utilities.object.ObjectUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Auto-generated Javadoc
/** The Class ArrayUtils. */
public class ArrayUtils {

  // Generic function to convert an Array to List
  /**
   * From array to list.
   *
   * @param <T> the generic type
   * @param array the array
   * @return the list
   */
  public static <T> List<T> fromArrayToList(T[] array) {
    if (!ObjectUtils.isEmpty(array)) {
      /*
       * // Create an empty List List<T> list = new ArrayList<>(); // Iterate through the array for
       * (T t : array) { // Add each element into the list list.add(t); } // Return the converted
       * List return list;
       */
      return Arrays.stream(array).collect(Collectors.toList());
    } else return null;
  }

  /**
   * From array to collection.
   *
   * @param <T> the generic type
   * @param array the array
   * @param c the c
   */
  public static <T> void fromArrayToCollection(T[] array, Collection<T> c) {
    if (!ObjectUtils.isEmpty(array)) {
      for (T o : array) {
        c.add(o); // Correct
      }
    }
  }

  /**
   * Checks if is element in array.
   *
   * @param <T> the generic type
   * @param array the array
   * @param element the element
   * @return true, if is element in array
   */
  public static <T extends Object> boolean isElementInArray(T[] array, T element) {
    if (ObjectUtils.isNull(element) || ObjectUtils.isEmpty(array)) return false;
    else {
      Arrays.sort(array);
      int searchIndex = Arrays.binarySearch(array, element);
      return searchIndex >= 0;
    }
  }

  /**
   * Checks if is element in array.
   *
   * @param array the array
   * @param element the element
   * @return true, if is element in array
   */
  public static boolean isElementInArray(int[] array, int element) {
    if (ObjectUtils.isNull(element) || ObjectUtils.isNull(array)) return false;
    else {
      Arrays.sort(array);
      int searchIndex = Arrays.binarySearch(array, element);
      return searchIndex >= 0;
    }
  }

  /**
   * Checks if is element in array.
   *
   * @param array the array
   * @param element the element
   * @return true, if is element in array
   */
  public static boolean isElementInArray(double[] array, double element) {
    if (ObjectUtils.isNull(element) || ObjectUtils.isNull(array)) return false;
    else {
      Arrays.sort(array);
      int searchIndex = Arrays.binarySearch(array, element);
      return searchIndex >= 0;
    }
  }

  /**
   * Checks if is element in array.
   *
   * @param array the array
   * @param element the element
   * @return true, if is element in array
   */
  public static boolean isElementInArray(float[] array, float element) {
    if (ObjectUtils.isNull(element) || ObjectUtils.isNull(array)) return false;
    else {
      Arrays.sort(array);
      int searchIndex = Arrays.binarySearch(array, element);
      return searchIndex >= 0;
    }
  }

  /**
   * Checks if is element in array.
   *
   * @param array the array
   * @param element the element
   * @return true, if is element in array
   */
  public static boolean isElementInArray(short[] array, short element) {
    if (ObjectUtils.isNull(element) || ObjectUtils.isNull(array)) return false;
    else {
      Arrays.sort(array);
      int searchIndex = Arrays.binarySearch(array, element);
      return searchIndex >= 0;
    }
  }

  /**
   * Checks if is element in array.
   *
   * @param array the array
   * @param element the element
   * @return true, if is element in array
   */
  public static boolean isElementInArray(char[] array, char element) {
    if (ObjectUtils.isNull(element) || ObjectUtils.isNull(array)) return false;
    else {
      Arrays.sort(array);
      int searchIndex = Arrays.binarySearch(array, element);
      return searchIndex >= 0;
    }
  }

  /*
   * public final void testIsElementInArray() { byte byteArr[] = {10, 20, 15, 22, 35}; char
   * charArr[] = {'g', 'p', 'q', 'c', 'i'}; int intArr[] = {10, 20, 15, 22, 35}; double doubleArr[]
   * = {10.2, 15.1, 2.2, 3.5}; float floatArr[] = {10.2f, 15.1f, 2.2f, 3.5f}; short shortArr[] =
   * {10, 20, 15, 22, 35}; Integer arr[] = {1};
   *
   * Arrays.sort(byteArr); Arrays.sort(charArr); Arrays.sort(intArr); Arrays.sort(doubleArr);
   * Arrays.sort(floatArr); Arrays.sort(shortArr);
   *
   * byte byteKey = 35; char charKey = 'g'; int intKey = 22; double doubleKey = 1.5; float floatKey
   * = 35; short shortKey = 5;
   *
   * // System.out.println(byteKey + " found at index = " + // Arrays.binarySearch(byteArr,
   * byteKey)); // System.out.println(charKey + " found at index = " + //
   * Arrays.binarySearch(charArr, charKey)); // System.out.println(intKey + " found at index = " +
   * // Arrays.binarySearch(intArr, intKey)); // System.out.println(doubleKey + " found at index = "
   * + // Arrays.binarySearch(doubleArr, doubleKey)); // System.out.println(floatKey +
   * " found at index = " + // Arrays.binarySearch(floatArr, floatKey)); //
   * System.out.println(shortKey + " found at index = " + // Arrays.binarySearch(shortArr,
   * shortKey)); String[] sa = new String[100]; Collection<String> cs = new ArrayList<String>();
   * Collection<Integer> cs1 = new ArrayList<Integer>(); // T inferred to be String
   * ArrayUtils.fromArrayToCollection(sa, cs); }
   */
}
