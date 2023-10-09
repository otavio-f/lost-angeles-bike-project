package br.otaviof.transformations;

public class Util {

    public static String[] filterColumn(String[] column, int count, boolean[] filter) {
        String[] result = new String[count];
        int inc = 0;
        for (int i=0; i<column.length; i++) {
            if (filter[i]) {
                result[inc] = column[i];
                inc++;
            }
        }
        return result;
    }

    public static Integer[] filterColumn(Integer[] column, int count, boolean[] filter) {
        Integer[] result = new Integer[count];
        int inc = 0;
        for (int i=0; i<column.length; i++) {
            if (filter[i]) {
                result[inc] = column[i];
                inc++;
            }
        }
        return result;
    }

    public static void reverseArray(Object[] arr) {
        for (int i=0; i<arr.length/2; i++)
            br.otaviof.sort.Util.swap(arr, i, arr.length-1-i);
    }
}
