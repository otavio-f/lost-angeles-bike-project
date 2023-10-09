package br.otaviof.sort.methods;

import br.otaviof.sort.Util;

import java.util.logging.Logger;

public class Selection implements Sorter {

    /***
     * Sort an array using the selection sort method
     * @param target the array to be sorted
     * @param reverse the sorting order
     */
    public <T extends Comparable<T>> int[] sort(T[] target, boolean reverse) {
        int[] positions = Sorter.getDefaultPositions(target.length);
        int selected;
        for (int i=0; i<target.length; i++) {
            selected = reverse ? Util.max(target, i) : Util.min(target, i);
            Util.swap(target, i, selected);
            Util.swap(positions, i, selected);
        }
        return positions;
    }

    public static <T extends Comparable<T>> int[] sort(T[] arr) {
        return new Selection().sort(arr, false);
    }

    public static <T extends Comparable<T>> int[] sortDesc(T[] arr) {
        return new Selection().sort(arr, true);
    }
}