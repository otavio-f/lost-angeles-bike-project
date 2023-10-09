package br.otaviof.sort;

import br.otaviof.sort.methods.*;
import br.otaviof.sort.sampleGenerators.SampleGenerator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static br.otaviof.sort.assertions.CustomAssert.assertAscending;
import static br.otaviof.sort.assertions.CustomAssert.assertDescending;

public class SortersTest {
    private static final int SAMPLE_SIZE = 8192; // estimate number of records
    private static final int MAX_WORD_LENGTH = 32; // estimate size of longest word

    private Integer[] ints;
    private String[] strings;
    private LocalDate[] dates;

    @Before
    public void setup() {
        SampleGenerator.resetGenerator();
        dates = SampleGenerator.getDates(SAMPLE_SIZE);
        ints = SampleGenerator.getIntegers(SAMPLE_SIZE);
        strings = SampleGenerator.getStringSamples(SAMPLE_SIZE, MAX_WORD_LENGTH);
    }


    @Test
    public void testSelectionSortAscendingOrder() {
        Selection.sort(ints);
        Selection.sort(dates);
        Selection.sort(strings);

        assertAscending(ints);
        assertAscending(dates);
        assertAscending(strings);
    }

    @Test
    public void testSelectionSortDescendingOrder() {
        Selection.sortDesc(ints);
        Selection.sortDesc(dates);
        Selection.sortDesc(strings);

        assertDescending(ints);
        assertDescending(dates);
        assertDescending(strings);
    }

    @Test
    public void testInsertionSortAscendingOrder() {
        Insertion.sort(ints);
        Insertion.sort(dates);
        Insertion.sort(strings);

        assertAscending(ints);
        assertAscending(dates);
        assertAscending(strings);
    }

    @Test
    public void testInsertionSortDescendingOrder() {
        Insertion.sortDesc(ints);
        Insertion.sortDesc(dates);
        Insertion.sortDesc(strings);

        assertDescending(ints);
        assertDescending(dates);
        assertDescending(strings);
    }

    @Test
    public void testMergeSortAscendingOrder() {
        Merge.sort(ints);
        Merge.sort(dates);
        Merge.sort(strings);

        assertAscending(ints);
        assertAscending(dates);
        assertAscending(strings);
    }

    @Test
    public void testMergeSortDescendingOrder() {
        Merge.sortDesc(ints);
        Merge.sortDesc(dates);
        Merge.sortDesc(strings);

        assertDescending(ints);
        assertDescending(dates);
        assertDescending(strings);
    }

    @Test
    public void testQuickSortAscendingOrder() {
        Quick.sort(ints);
        Quick.sort(dates);
        Quick.sort(strings);

        assertAscending(ints);
        assertAscending(dates);
        assertAscending(strings);
    }

    @Test
    public void testQuickSortDescendingOrder() {
        Quick.sortDesc(ints);
        Quick.sortDesc(dates);
        Quick.sortDesc(strings);

        assertDescending(ints);
        assertDescending(dates);
        assertDescending(strings);
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testCountingSortFailsOnDates() {
        Counting.sort(dates);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCountingSortFailsOnStrings() {
        Counting.sort(strings);
    }

    @Test
    public void testCountingSortAscendingOrder() {
        final int MAXIMUM = 32768;
        final int MINIMUM = -32768;
        ints = SampleGenerator.getIntegers(SAMPLE_SIZE, MINIMUM, MAXIMUM);

        Counting.sort(ints);
        assertAscending(ints);
    }

    @Test
    public void testCountingSortDescendingOrder() {
        final int MAXIMUM = 32768;
        final int MINIMUM = -32768;
        ints = SampleGenerator.getIntegers(SAMPLE_SIZE, MINIMUM, MAXIMUM);

        Counting.sortDesc(ints);
        assertDescending(ints);
    }


    @Test
    public void testHeapSortAscendingOrder() {
        Heap.sort(ints);
        Heap.sort(dates);
        Heap.sort(strings);

        assertAscending(ints);
        assertAscending(dates);
        assertAscending(strings);
    }

    @Test
    public void testHeapSortDescendingOrder() {
        Heap.sortDesc(ints);
        Heap.sortDesc(dates);
        Heap.sortDesc(strings);

        assertDescending(ints);
        assertDescending(dates);
        assertDescending(strings);
    }
}
