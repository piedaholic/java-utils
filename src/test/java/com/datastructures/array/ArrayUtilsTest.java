/**
 * 
 */
package com.datastructures.array;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

/**
 * @author hpsingh
 *
 */
public class ArrayUtilsTest {

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#newHashSetWithExpectedSize(int)}.
     */
    @Test
    public final void testNewHashSetWithExpectedSize() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#newHashMapWithExpectedSize(int)}.
     */
    @Test
    public final void testNewHashMapWithExpectedSize() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.datastructures.array.ArrayUtils#capacity(int)}.
     */
    @Test
    public final void testCapacity() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#checkEntryNotNull(java.lang.Object, java.lang.Object)}.
     */
    @Test
    public final void testCheckEntryNotNull() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#checkNonnegative(int, java.lang.String)}.
     */
    @Test
    public final void testCheckNonnegativeIntString() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#checkNonnegative(long, java.lang.String)}.
     */
    @Test
    public final void testCheckNonnegativeLongString() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#checkPositive(int, java.lang.String)}.
     */
    @Test
    public final void testCheckPositive() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#checkRemove(boolean)}.
     */
    @Test
    public final void testCheckRemove() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#checkState(boolean)}.
     */
    @Test
    public final void testCheckStateBoolean() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#checkState(boolean, java.lang.Object)}.
     */
    @Test
    public final void testCheckStateBooleanObject() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.datastructures.array.ArrayUtils#newHashSet(E[])}.
     */
    @Test
    public final void testNewHashSet() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.datastructures.array.ArrayUtils#newTreeSet(E[])}.
     */
    @Test
    public final void testNewTreeSet() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#newSortedTreeSet(E[])}.
     */
    @Test
    public final void testNewSortedTreeSet() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#convertArrayToList(T[])}.
     */
    @Test
    public final void testConvertArrayToList() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.datastructures.array.ArrayUtils#isElementInArray(T[], java.lang.Object)}.
     */
    @Test
    public final void testIsElementInArray() {
	byte byteArr[] = { 10, 20, 15, 22, 35 };
	char charArr[] = { 'g', 'p', 'q', 'c', 'i' };
	int intArr[] = { 10, 20, 15, 22, 35 };
	double doubleArr[] = { 10.2, 15.1, 2.2, 3.5 };
	float floatArr[] = { 10.2f, 15.1f, 2.2f, 3.5f };
	short shortArr[] = { 10, 20, 15, 22, 35 };

	Arrays.sort(byteArr);
	Arrays.sort(charArr);
	Arrays.sort(intArr);
	Arrays.sort(doubleArr);
	Arrays.sort(floatArr);
	Arrays.sort(shortArr);

	byte byteKey = 35;
	char charKey = 'g';
	int intKey = 22;
	double doubleKey = 1.5;
	float floatKey = 35;
	short shortKey = 5;

	// System.out.println(byteKey + " found at index = " +
	// Arrays.binarySearch(byteArr, byteKey));
	// System.out.println(charKey + " found at index = " +
	// Arrays.binarySearch(charArr, charKey));
	// System.out.println(intKey + " found at index = " +
	// Arrays.binarySearch(intArr, intKey));
	// System.out.println(doubleKey + " found at index = " +
	// Arrays.binarySearch(doubleArr, doubleKey));
	// System.out.println(floatKey + " found at index = " +
	// Arrays.binarySearch(floatArr, floatKey));
	// System.out.println(shortKey + " found at index = " +
	// Arrays.binarySearch(shortArr, shortKey));
	String[] sa = new String[100];
	Collection<String> cs = new ArrayList<String>();
	Collection<Integer> cs1 = new ArrayList<Integer>();
	// T inferred to be String
	ArrayUtils.fromArrayToCollection(sa, cs);

    }
}
