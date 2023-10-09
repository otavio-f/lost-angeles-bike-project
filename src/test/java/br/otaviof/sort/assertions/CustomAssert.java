package br.otaviof.sort.assertions;

import static org.junit.Assert.fail;

public class CustomAssert {
    /***
     * Checks if the array is in ascending order
     * @param target the array to be checked
     */
    public static <T extends Comparable<T>> void assertAscending(T[] target) {
        for (int i=1; i<target.length; i++) {
            if (target[i-1].compareTo(target[i]) > 0) // previous is bigger, fail
                fail("The array is not in ascending order");
        }
    }

    /***
     * Checks if the array is in descending order
     * @param target the array to be checked
     */
    public static <T extends Comparable<T>> void assertDescending(T[] target) {
        for (int i=1; i<target.length; i++) {
            if (target[i-1].compareTo(target[i]) < 0) // previous is smaller, fail
                fail("The array is not in descending order.");
        }
    }
}
