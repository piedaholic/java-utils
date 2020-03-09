package com.utilities.datatypes;

import java.io.*;
import java.util.*;

public class Sort {

    // Complete the bigSorting function below.
    static String[] bigSorting(String[] unsorted) {
	Comparator<String> compStr = new Comparator<String>() {

	    @Override
	    public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		if (o1.length() - o2.length() == 0) {
		    return o1.compareTo(o2);

		}
		return o1.length() - o2.length();
	    }
	};
	Arrays.parallelSort(unsorted, compStr);
	return unsorted;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
	// BufferedWriter bufferedWriter = new BufferedWriter(new
	// FileWriter(System.getenv("OUTPUT_PATH")));

	int n = scanner.nextInt();
	scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

	String[] unsorted = new String[n];

	for (int i = 0; i < n; i++) {
	    String unsortedItem = scanner.nextLine();
	    unsorted[i] = unsortedItem;
	}

	String[] result = bigSorting(unsorted);

	for (int i = 0; i < result.length; i++) {
	    // bufferedWriter.write(result[i]);
	    System.out.print(result[i]);

	    if (i != result.length - 1) {
		// bufferedWriter.write("\n");
		System.out.println("");
	    }
	}

	System.out.println("");
	// bufferedWriter.newLine();

	// bufferedWriter.close();

	scanner.close();
    }
}
