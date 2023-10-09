package br.otaviof.sort.methods;

/***
 * Class used as base for sorting methods
 */
public interface Sorter {
    /***
     * Returns an array with default positioning
     * @param arraySize the size of the array
     * @return a crescent array
     */
    public static int[] getDefaultPositions(int arraySize) {
        int[] result = new int[arraySize];
        for (int i=0; i<arraySize; i++)
            result[i] = i;
        return result;
    }

    // TODO: Add a way to track positions, maybe add an int array and swap it's positions together with the sorted array
    /***
     * Sort an array in place
     * @param arr the array to be sorted
     * @param reverse true to sort in descending order, false to sort in ascending
     * @param <T> a type that implements the comparable interface
     */
    public <T extends Comparable<T>> int[] sort(T[] arr, boolean reverse);

    public static <T extends Comparable<T>> int[] sort(T[] arr) {
        throw new RuntimeException();
    }

    public static <T extends Comparable<T>> int[] sortDesc(T[] arr) {
        throw new RuntimeException();
    }
}
