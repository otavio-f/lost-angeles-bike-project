package br.otaviof.sort;

import br.otaviof.sort.methods.Sorter;

/***
 * Utility methods for sorting
 */
public class Util {

    /***
     * swaps elements in place
     * @param arr the target array
     * @param i the index of an element
     * @param j the index of another element
     */
    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /***
     * swaps elements in place
     * @param arr the target array
     * @param i the index of an element
     * @param j the index of another element
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    /***
     * Checks if the element in the index is smaller than other
     * @param arr the target array
     * @param target the index of the target element for the comparison
     * @param other the index of the element to be compared against
     * @return true if the target element is smaller
     */
    public static <T extends Comparable<T>> boolean isSmaller(T[] arr, int target, int other) {
        return arr[target].compareTo(arr[other]) < 0;
    }

    /***
     * Finds the biggest element in the subarray
     * @param arr the array to be searched
     * @param first the first element of a subarray
     * @param last the last element of a subarray
     * @return the index of the biggest element in the subarray
     * @param <T> a comparable type
     */
    public static <T extends Comparable<T>> int max(T[] arr, int first, int last) {
        int result = first;
        for (int i=first+1; i<last; i++) {
            if (isBigger(arr, i, result))
            // if (arr[i].compareTo(arr[result]) > 0)
                result = i;
        }
        return result;
    }

    /***
     * Finds the biggest element in the subarray
     * @param arr the array to be searched
     * @param first the first element of a subarray
     * @return the index of the biggest element in the subarray
     * @param <T> a comparable type
     */
    public static <T extends Comparable<T>> int max(T[] arr, int first) {
        return max(arr, first, arr.length);
    }

    /***
     * Finds the biggest element in the array
     * @param arr the array to be searched
     * @return the index of the biggest element in the subarray
     * @param <T> a comparable type
     */
    public static <T extends Comparable<T>> int max(T[] arr) {
        return max(arr, 0, arr.length);
    }

    /***
     * Finds the smallest element in the subarray
     * @param arr the array to be searched
     * @param first the first element of a subarray
     * @param last the last element of a subarray
     * @return the index of the smallest element in the subarray
     * @param <T> a comparable type
     */
    public static <T extends Comparable<T>> int min(T[] arr, int first, int last) {
        int result = first;
        for (int i=first+1; i<last; i++) {
            if (isSmaller(arr, i, result))
            //if (arr[i].compareTo(arr[result]) < 0)
                result = i;
        }
        return result;
    }

    /***
     * Finds the smallest element in the subarray
     * @param arr the array to be searched
     * @param first the first element of a subarray
     * @return the index of the biggest element in the subarray
     * @param <T> a comparable type
     */
    public static <T extends Comparable<T>> int min(T[] arr, int first) {
        return min(arr, first, arr.length);
    }

    /***
     * Finds the biggest element in the array
     * @param arr the array to be searched
     * @return the index of the biggest element in the subarray
     * @param <T> a comparable type
     */
    public static <T extends Comparable<T>> int min(T[] arr) {
        return min(arr, 0, arr.length);
    }

    /***
     * Checks if the element in the index is bigger than other
     * @param arr the target array
     * @param target the index of the target element for the comparison
     * @param other the index of the element to be compared against
     * @return true if the target element is bigger
     */
    public static <T extends Comparable<T>> boolean isBigger(T[] arr, int target, int other) {
        return arr[target].compareTo(arr[other]) > 0;
    }
}
