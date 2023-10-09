package br.otaviof.sort.methods;

import br.otaviof.sort.Util;

public class Quick implements Sorter {

    /**
     * Lomuto's partition
     * @param arr array to be partitioned
     * @param low the low partition index
     * @param high the high index
     * @return the final index of the partition
     * @param <T> a comparable type
     */
    private <T extends Comparable<T>> int partition(T[] arr, int low, int high, boolean reverse, int[] positions) {
        int i = (low - 1);

        for (int j=low; j<high; j++) { //pivot: arr[high]
            if (reverse ? !Util.isSmaller(arr, j, high) : !Util.isBigger(arr, j, high)) { // >= or <=
                i++;
                Util.swap(arr, i, j);
                Util.swap(positions, i, j);
            }
        }
        Util.swap(arr, i+1, high);
        Util.swap(positions, i+1, high);
        return i+1;
    }

    private <T extends Comparable<T>> void sortRecursive(T[] arr, int low, int high, boolean reverse, int[] positions) {
        // many items cause stack overflow!
        while (low < high) {
            int partIndex = partition(arr, low, high, reverse, positions);

            sortRecursive(arr, low, partIndex-1 ,reverse, positions);
            low = partIndex+1;
        }
    }

    private <T extends Comparable<T>> void sort(T[] arr, int low, int high, boolean reverse, int[] positions) {
        int[] stack = new int[high-low+1];
        int top = -1;
        stack[++top] = low;
        stack[++top] = high;

        while (top >= 0) {
            high = stack[top--];
            low = stack[top--];
            int partIndex = partition(arr, low, high, reverse, positions);

            if (partIndex-1 > low) {
                stack[++top] = low;
                stack[++top] = partIndex-1;
            }

            if (partIndex+1 < high) {
                stack[++top] = partIndex+1;
                stack[++top] = high;
            }
        }
    }

    /**
     * @param arr
     * @param reverse
     * @param <T>
     */
    @Override
    public <T extends Comparable<T>> int[] sort(T[] arr, boolean reverse) {
        int[] positions = Sorter.getDefaultPositions(arr.length);
        sort(arr, 0, arr.length-1, reverse, positions);
        // sortRecursive(arr, 0, arr.length-1, reverse, positions);
        // stack overflow with recursive

        return positions;
    }

    public static <T extends Comparable<T>> int[] sort(T[] arr) {
        return new Quick().sort(arr, false);
    }

    public static <T extends Comparable<T>> int[] sortDesc(T[] arr) {
        return new Quick().sort(arr, true);
    }
}
