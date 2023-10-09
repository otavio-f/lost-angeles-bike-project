package br.otaviof.sort.sampleGenerators;

import java.time.LocalDate;
import java.util.Random;

/***
 * Collection of utility methods to generate random samples
 */
public class SampleGenerator {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static Random generator;

    private SampleGenerator() { }

    /***
     * Resets the internal random generator state. Must be called at least once before calling any other sample generating method
     */
    public static void resetGenerator() {
        generator = new Random();
    }

    /***
     * Generates words from random lowercase letters
     * @param maxLength the maximum length of a word
     * @return a word with length from 1 to maxLength
     */
    private static String genWord(int maxLength) {
        StringBuilder word = new StringBuilder();
        int choice;
        int wordLength = generator.nextInt(maxLength)+1; //between 1 and maxLength characters
        do {
            choice = generator.nextInt(ALPHABET.length());
            word.append(ALPHABET.charAt(choice));
        } while (word.length() < wordLength);
        return word.toString();
    }

    /***
     * Generates an array of randomly generated words of random length
     * @param sampleAmount size of the array to be generated
     * @param maxLength max size of each word. The size of each generated word will be between 1 and maxLength
     * @return An array of randomly generated words
     */
    public static String[] getStringSamples(int sampleAmount, int maxLength) {
        if (sampleAmount <= 0)
            throw new IllegalArgumentException("Invalid sample amount.");
        if (maxLength <= 0)
            throw new IllegalArgumentException("Invalid word maximum length");
        String[] result = new String[sampleAmount];
        for (int i=0; i<sampleAmount; i++)
            result[i] = genWord(maxLength);
        return result;
    }

    /***
     * Pick randomly a number in the selected range
     * @param min the minimum possible integer, inclusive
     * @param max the maximum possible integer, inclusive
     * @return a long in the selected range
     */
    private static long randomIntegerInRange(long min, long max) {
        long range = 1+(max-min);
        double choice = generator.nextDouble();
        return min + ((long) (range*choice));
    }

    /***
     * Generate an array of random integers
     * @param sampleAmount size of the array to be generated
     * @return An array of integers
     */
    public static Integer[] getIntegers(int sampleAmount) {
        return getIntegers(sampleAmount, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /***
     * Generate an array of random positive integers
     * @param sampleAmount size of the array to be generated
     * @param max the biggest integer that can be included in the array
     * @return An array of integers
     */
    public static Integer[] getIntegers(int sampleAmount, int max) {
        return getIntegers(sampleAmount, 0, max);
    }

    /***
     * Generate an array of random integers
     * @param sampleAmount size of the array to be generated
     * @param min the smallest integer that can be included in the array
     * @param max the biggest integer that can be included in the array
     * @return An array of integers
     */
    public static Integer[] getIntegers(int sampleAmount, int min, int max) {
        if (sampleAmount <= 0)
            throw new IllegalArgumentException("Invalid sample amount.");
        if (min >= max)
            throw new IllegalArgumentException("Invalid interval. Minimum should be smaller than maximum.");
        Integer[] result = new Integer[sampleAmount];
        int choice;
        for (int i=0; i<sampleAmount; i++) {
            choice = (int) (randomIntegerInRange(min, max));
            result[i] = choice;
        }
        return result;
    }
    /***
     * Generate an array of random Epoch-based date-times
     * @param sampleAmount the size of array to be generated
     * @return An array of date-times
     */
    public static LocalDate[] getDates(int sampleAmount) {
        if (sampleAmount <= 0)
            throw new IllegalArgumentException("Invalid sample amount.");
        LocalDate[] result = new LocalDate[sampleAmount];
        long choice;

        for (int i=0; i<sampleAmount; i++) {
            choice = randomIntegerInRange(LocalDate.MIN.toEpochDay(), LocalDate.MAX.toEpochDay());
            //choice = randomIntegerInRange(-999999999, 999999999);
            result[i] = LocalDate.ofEpochDay(choice);
        }
        return result;
    }
}
