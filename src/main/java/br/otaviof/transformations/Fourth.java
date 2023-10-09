package br.otaviof.transformations;

import br.otaviof.data.TabulatedDataSource;
import br.otaviof.sort.methods.Heap;
import br.otaviof.sort.methods.Quick;
import br.otaviof.sort.methods.Sorter;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import static br.otaviof.transformations.Util.filterColumn;

public class Fourth implements Transformation {
    private class OrderResult {
        public final int[] order;
        public final double elapsedTime; //in seconds

        private OrderResult(int[] order, long elapsedTime) {
            this.order = order;
            this.elapsedTime = elapsedTime/1_000_000_000.0;;
        }
    }

    private final TabulatedDataSource source;

    public Fourth(String source) {
        this.source = new TabulatedDataSource(source, ',');
    }

    private OrderResult doSort(Sorter method, String[] arr) {
        String[] copy = Arrays.copyOf(arr, arr.length);
        long startTime = System.nanoTime();
        int[] order = method.sort(copy, false);
        long elapsed = System.nanoTime() - startTime;
        return new OrderResult(order, elapsed);
    }

    private void doSave(int[] order, String output) throws IOException {
        //TODO: Something wrong with ordering function!!
        Integer[] trip_ids = source.getIntColumn("trip_id", order);
        Integer[] durations = source.getIntColumn("duration", order);
        String[] start_times = source.getColumn("start_time", order);
        String[] end_times = source.getColumn("end_time", order);
        Integer[] bike_ids = source.getIntColumn("bike_id", order);
        String[] bike_types = source.getColumn("bike_type", order);
        String[] trip_route_categories = source.getColumn("trip_route_category", order);
        Integer[] plan_durations = source.getIntColumn("plan_duration", order);
        String[] passholder_types = source.getColumn("passholder_type", order);
        String[] start_stations = source.getColumn("start_station", order);

        this.source.writeColumn(output, "trip_id", trip_ids);
        this.source.writeColumn(output, "duration", durations);
        this.source.writeColumn(output, "start_time", start_times);
        this.source.writeColumn(output, "end_time", end_times);
        this.source.writeColumn(output, "bike_id", bike_ids);
        this.source.writeColumn(output, "bike_type", bike_types);
        this.source.writeColumn(output, "trip_route_category", trip_route_categories);
        this.source.writeColumn(output, "plan_duration", plan_durations);
        this.source.writeColumn(output, "passholder_type", passholder_types);
        this.source.writeColumn(output, "start_station", start_stations);
    }

    @Override
    public void transform(String output) throws IOException {
        Logger logger = Logger.getLogger("SecondTransform");

        logger.info("Fetching data from csv.");
        String[] averageCase = source.getColumn("start_station");

        logger.info("Generating test cases.");
        String[] bestCase = Arrays.copyOf(averageCase, averageCase.length);
        Arrays.sort(bestCase);
        String[] worstCase = Arrays.copyOf(bestCase, bestCase.length);
        Util.reverseArray(worstCase);

        OrderResult result;
        // HEAP SORT
        logger.info("Measuring Heap Sort.");
        result = doSort(new Heap(), bestCase);
        logger.info(String.format("Took %.2f seconds", result.elapsedTime));
        doSave(result.order, "LAMetroTrips_station_quickSort_melhorCaso.csv");

        result = doSort(new Heap(), averageCase);
        logger.info(String.format("Took %.2f seconds", result.elapsedTime));
        doSave(result.order, "LAMetroTrips_station_quickSort_medioCaso.csv");

        result = doSort(new Heap(), worstCase);
        doSave(result.order, "LAMetroTrips_station_quickSort_piorCaso.csv");
    }
}
