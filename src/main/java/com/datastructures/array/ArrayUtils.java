package com.datastructures.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.utilities.object.ObjectUtils;

public class ArrayUtils {

    /**
     * The number of bytes required to represent a primitive {@code int} value.
     *
     * <p>
     * <b>Java 8 users:</b> use {@link Integer#BYTES} instead.
     */
    public static final int BYTES = Integer.SIZE / Byte.SIZE;

    /**
     * The largest power of two that can be represented as an {@code int}.
     *
     * @since 10.0
     */
    public static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

    /**
     * Returns a new hash set using the smallest initial table size that can hold
     * {@code expectedSize} elements without resizing. Note that this is not what
     * {@link HashSet#HashSet(int)} does, but it is what most users want and expect
     * it to do.
     *
     * <p>
     * This behavior can't be broadly guaranteed, but has been tested with OpenJDK
     * 1.7 and 1.8.
     *
     * @param expectedSize
     *            the number of elements you expect to add to the returned set
     * @return a new, empty hash set with enough capacity to hold
     *         {@code expectedSize} elements without resizing
     * @throws IllegalArgumentException
     *             if {@code expectedSize} is negative
     */
    public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
	return new HashSet<E>(capacity(expectedSize));
    }

    /**
     * Creates a {@code HashMap} instance, with a high enough "initial capacity"
     * that it <i>should</i> hold {@code expectedSize} elements without growth. This
     * behavior cannot be broadly guaranteed, but it is observed to be true for
     * OpenJDK 1.7. It also can't be guaranteed that the method isn't inadvertently
     * <i>oversizing</i> the returned map.
     *
     * @param expectedSize
     *            the number of entries you expect to add to the returned map
     * @return a new, empty {@code HashMap} with enough capacity to hold
     *         {@code expectedSize} entries without resizing
     * @throws IllegalArgumentException
     *             if {@code expectedSize} is negative
     */
    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
	return new HashMap<>(capacity(expectedSize));
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as
     * long as it grows no larger than expectedSize and the load factor is ≥ its
     * default (0.75).
     */
    static int capacity(int expectedSize) {
	if (expectedSize < 3) {
	    checkNonnegative(expectedSize, "expectedSize");
	    return expectedSize + 1;
	}
	if (expectedSize < MAX_POWER_OF_TWO) {
	    // This is the calculation used in JDK8 to resize when a putAll
	    // happens; it seems to be the most conservative calculation we
	    // can make. 0.75 is the default load factor.
	    return (int) ((float) expectedSize / 0.75F + 1.0F);
	}
	return Integer.MAX_VALUE; // any large value
    }

    static void checkEntryNotNull(Object key, Object value) {
	if (key == null) {
	    throw new NullPointerException("null key in entry: null=" + value);
	} else if (value == null) {
	    throw new NullPointerException("null value in entry: " + key + "=null");
	}
    }

    static int checkNonnegative(int value, String name) {
	if (value < 0) {
	    throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
	}
	return value;
    }

    static long checkNonnegative(long value, String name) {
	if (value < 0) {
	    throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
	}
	return value;
    }

    static void checkPositive(int value, String name) {
	if (value <= 0) {
	    throw new IllegalArgumentException(name + " must be positive but was: " + value);
	}
    }

    /**
     * Precondition tester for {@code Iterator.remove()} that throws an exception
     * with a consistent error message.
     */
    static void checkRemove(boolean canRemove) {
	checkState(canRemove, "no calls to next() since the last call to remove()");
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression
     *            a boolean expression
     * @throws IllegalStateException
     *             if {@code expression} is false
     * @see Verify#verify Verify.verify()
     */
    public static void checkState(boolean expression) {
	if (!expression) {
	    throw new IllegalStateException();
	}
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression
     *            a boolean expression
     * @param errorMessage
     *            the exception message to use if the check fails; will be converted
     *            to a string using {@link String#valueOf(Object)}
     * @throws IllegalStateException
     *             if {@code expression} is false
     * @see Verify#verify Verify.verify()
     */
    public static void checkState(boolean expression, Object errorMessage) {
	if (!expression) {
	    throw new IllegalStateException(String.valueOf(errorMessage));
	}
    }

    /**
     * Creates a <i>mutable</i> {@code HashSet} instance initially containing the
     * given elements.
     *
     * <p>
     * <b>Note:</b> if elements are non-null and won't be added or removed after
     * this point, use {@link ImmutableSet#of()} or
     * {@link ImmutableSet#copyOf(Object[])} instead. If {@code E} is an
     * {@link Enum} type, use {@link EnumSet#of(Enum, Enum[])} instead. Otherwise,
     * strongly consider using a {@code LinkedHashSet} instead, at the cost of
     * increased memory footprint, to get deterministic iteration behavior.
     *
     * <p>
     * This method is just a small convenience, either for
     * {@code newHashSet(}{@link Arrays#asList asList}{@code (...))}, or for
     * creating an empty set then calling {@link Collections#addAll}. This method is
     * not actually very useful and will likely be deprecated in the future.
     */
    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... elements) {
	HashSet<E> set = newHashSetWithExpectedSize(elements.length);
	Collections.addAll(set, elements);
	return set;
    }

    @SafeVarargs
    public static <E> TreeSet<E> newTreeSet(E... elements) {
	TreeSet<E> set = new TreeSet<>();
	Collections.addAll(set, elements);
	return set;
    }

    @SafeVarargs
    public static <E> TreeSet<E> newSortedTreeSet(E... elements) {
	TreeSet<E> set = new TreeSet<>();
	Collections.addAll(set, elements);
	return set;
    }

    // Generic function to convert an Array to List
    public static <T> List<T> convertArrayToList(T array[]) {

	// Create an empty List
	List<T> list = new ArrayList<>();

	// Iterate through the array
	for (T t : array) {
	    // Add each element into the list
	    list.add(t);
	}

	// Return the converted List
	return list;
    }

    public static <T extends Object> boolean isElementInArray(T[] array, T element) {
	if (ObjectUtils.isNull(element) || ObjectUtils.isNull(array))
	    return false;
	else {
	    Arrays.sort(array);
	    int searchIndex = Arrays.binarySearch(array, element);
	    return searchIndex >= 0;
	}
    }

    public <T> List<T> fromArrayToList(T[] a) {
	return Arrays.stream(a).collect(Collectors.toList());
    }

    public static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
	for (T o : a) {
	    c.add(o); // Correct
	}
    }

}
