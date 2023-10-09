package br.otaviof.sort.methods;

public class Merge implements Sorter {

    private <T extends Comparable<T>> int[] recursiveDivide(T[] arr, int start, int end, boolean reverse) {
        // stack overflow with only 256 items!
        int[] positions = Sorter.getDefaultPositions(arr.length);
        if (start > end)
            return positions;
        int middle = (start+end)/2;
        recursiveDivide(arr, start, middle, reverse);
        recursiveDivide(arr, middle+1, end, reverse);
        merge(arr, start, middle, end, reverse, positions);
        return positions;
    }

    private <T extends Comparable<T>> void merge(T[] arr, int start, int middle, int end, boolean reverse, int[] positions) {
        //find temp array sizes
        int sizeLeft = middle-start+1;
        int sizeRight = end-middle;

        // create and copy temp arrays
        Comparable[] left = new Comparable[sizeLeft];
        for (int i=0; i < sizeLeft; i++)
            left[i] = arr[start+i];

        Comparable[] right = new Comparable[sizeRight];
        for (int i=0; i < sizeRight; i++)
            right[i] = arr[1+middle+i];

        // merge temp arrays into sorted array
        int i=0;
        int j=0;
        int k=start;
        while (i < sizeLeft && j < sizeRight) {
            int comparison = left[i].compareTo(right[j]);
            if (reverse ? (comparison > -1): (comparison < 1)) { // left[i] <= right[j] || left[i] >= right[j]
                arr[k] = (T) left[i];
                // TODO: modify index on <positions> to match the position of the inserted item
                i++;
            } else {
                arr[k] = (T) right[j];
                // TODO: modify index on <positions> to match the position of the inserted item
                j++;
            }
            k++;
        }

        // copy remaining from left side
        while (i < sizeLeft) {
            arr[k] = (T) left[i];
            // TODO: modify index on <positions> to match the position of the inserted item
            i++;
            k++;
        }

        // copy remaining from right side
        while (j < sizeRight) {
            arr[k] = (T) right[j];
            // TODO: modify index on <positions> to match the position of the inserted item
            j++;
            k++;
        }
    }


    /**
     * @param arr
     * @param reverse
     * @param <T>
     */
    @Override
    public <T extends Comparable<T>> int[] sort(T[] arr, boolean reverse) {
        // iterative bc recursive ends up in stack overflow
        // recursiveDivide(arr, 0, arr.length-1, reverse);
        int[] positions = Sorter.getDefaultPositions(arr.length);

        final int last = arr.length-1;
        for (int size=1; size <= last; size = 2*size) {
            for (int start=0; start < last; start += 2*size) {
                int mid = Math.min(start + size-1, last);
                int end = Math.min(start + (2*size) -1, last);
                merge(arr, start, mid, end, reverse, positions);
            }
        }
        return positions;
    }

    public static <T extends Comparable<T>> int[] sort(T[] arr) {
        return new Merge().sort(arr, false);
    }

    public static <T extends Comparable<T>> int[] sortDesc(T[] arr) {
        return new Merge().sort(arr, true);
    }
}
