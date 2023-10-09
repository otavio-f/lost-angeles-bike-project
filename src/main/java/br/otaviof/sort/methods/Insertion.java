package br.otaviof.sort.methods;

import br.otaviof.sort.Util;

/***
 * Class for insertion sort method
 */
public class Insertion implements Sorter {

    private <T extends Comparable<T>> void pushBack(T[] arr, int target, boolean reverse, int[] positions) {
        for (int j=target; j>0; j--) {
            int k = j-1;
            if (reverse ? Util.isBigger(arr, j, k) : Util.isSmaller(arr, j, k)) {
                Util.swap(arr, j, k);
                Util.swap(positions, j, k);
            }
            else
                break;
        }
    }
    /***
     * Sort an array in place
     * @param target the array to be sorted
     * @param reverse the sorting order
     */
    @Override
    public <T extends Comparable<T>> int[] sort(T[] target, boolean reverse) {
        int[] positions = Sorter.getDefaultPositions(target.length);
        for (int i=1; i<target.length; i++) {
            pushBack(target, i, reverse, positions);
        }
        return positions;
    }

    public static <T extends Comparable<T>> int[] sort(T[] arr) {
        return new Insertion().sort(arr, false);
    }

    public static <T extends Comparable<T>> int[] sortDesc(T[] arr) {
        return new Insertion().sort(arr, true);
    }
}
