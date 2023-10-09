package br.otaviof.sort.methods;

import br.otaviof.sort.Util;

import java.util.Arrays;

/***
 * Counting sort method. Can only sort integers
 */
public class Counting implements Sorter {

    /***
     * Sorts an array of integers using the counting sort method
     * @param arr the array to be sorted
     * @param reverse true to sort in descending order
     * @param <T> type of element to be sorted. Currently only integers are supported
     */
    @Override
    public <T extends Comparable<T>> int[] sort(T[] arr, boolean reverse) {
        if (!(arr instanceof Integer[]))
            throw new UnsupportedOperationException("Counting sort can only sort integers!");

        int[] positions = Sorter.getDefaultPositions(arr.length);
        T[] original = Arrays.copyOf(arr, arr.length);
        int min = (Integer) original[Util.min(original)];
        int max = (Integer) original[Util.max(original)];
        int[] aux = new int[(max-min)+1];

        for (T value : original) { //fill in counting array
            int index = (Integer) value - min;
            aux[index]++;
        }

        //cumulative sum
        if (reverse)
            for (int i=aux.length-1; i>0; i--)
                aux[i-1] += aux[i];
        else
            for (int i=1; i<aux.length; i++)
                aux[i] += aux[i-1];

        for (int i=original.length-1; i>=0; i--) {
            T elem = original[i];
            int index = ((Integer) elem) - min;
            arr[aux[index]-1] = elem;
            // TODO: modify index on <positions> to match the position of the inserted item
            aux[index]--;
        }
        return positions;
    }

    public static <T extends Comparable<T>> int[] sort(T[] arr) {
        return new Counting().sort(arr, false);
    }

    public static <T extends Comparable<T>> int[] sortDesc(T[] arr) {
        return new Counting().sort(arr, true);
    }
}
