package br.otaviof.sort.methods;

import br.otaviof.sort.Util;

public class Heap implements Sorter {
    public <T extends Comparable<T>> void turnIntoMinHeap(T[] arr, int size, int root, int[] positions) {
        int smallest = root;
        int left = (2 * root) + 1;
        int right = (2 * root) + 2;

        if (left < size && Util.isSmaller(arr, left, smallest))
            smallest = left;
        if (right < size && Util.isSmaller(arr, right, smallest))
            smallest = right;

        if (smallest != root) {
            Util.swap(arr, root, smallest);
            Util.swap(positions, root, smallest);
            turnIntoMinHeap(arr, size, smallest, positions);
        }
    }
    public <T extends Comparable<T>> void turnIntoMaxHeap(T[] arr, int size, int root, int[] positions) {
        int largest = root;
        int left = (2 * root) + 1;
        int right = (2 * root) + 2;

        if (left < size && Util.isBigger(arr, left, largest))
            largest = left;
        if (right < size && Util.isBigger(arr, right, largest))
            largest = right;

        if (largest != root) {
            Util.swap(arr, root, largest);
            Util.swap(positions, root, largest);
            turnIntoMaxHeap(arr, size, largest, positions);
        }
    }
    @Override
    public <T extends Comparable<T>> int[] sort(T[] arr, boolean reverse) {
        int[] positions = Sorter.getDefaultPositions(arr.length);
        int length = arr.length;
        for (int i=length/2-1; i>=0; i--) {
            if (reverse)
                turnIntoMinHeap(arr, length, i, positions);
            else
                turnIntoMaxHeap(arr, length, i, positions);
        }

        for (int i=length-1; i>=0; i--) {
            Util.swap(arr, 0, i);
            Util.swap(positions, 0, i);
            // TODO: modify index on <positions> to match the position of the inserted item
            if (reverse)
                turnIntoMinHeap(arr, i, 0, positions);
            else
                turnIntoMaxHeap(arr, i, 0, positions);
        }
        return positions;
    }

    public static <T extends Comparable<T>> int[] sort(T[] arr) {
        return new Heap().sort(arr, false);
    }

    public static <T extends Comparable<T>> int[] sortDesc(T[] arr) {
        return new Heap().sort(arr, true);
    }
}
