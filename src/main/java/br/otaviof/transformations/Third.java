package br.otaviof.transformations;

import br.otaviof.data.TabulatedDataSource;

import java.io.IOException;
import java.util.logging.Logger;

import static br.otaviof.transformations.Util.filterColumn;

/***
 * Third transformation, filters only with duration bigger than average
 */
public class Third implements Transformation {
    private final TabulatedDataSource source;

    public Third(String source) {
        this.source = new TabulatedDataSource(source, ',');
    }

    private double getAverage(Integer[] arr) {
        double result = 0.0;
        for (Integer val: arr)
            result += (double) val /arr.length;
        return result;
    }

    @Override
    public void transform(String output) throws IOException {
        Logger logger = Logger.getLogger("ThirdTransformation");
        logger.info("Fetching data from csv.");
        Integer[] durations = source.getIntColumn("duration");
        double avg = getAverage(durations);

        logger.info(String.format("Filtering records with duration larger than %.2f", avg));
        boolean[] filter = new boolean[durations.length];
        int count = 0;
        for(int i=0; i<durations.length; i++) {
            filter[i] = false;
            if (durations[i] > avg) {
                filter[i] = true;
                count++;
            }
        }

        logger.info(String.format("Gathering records with duration larger than %.2f", avg));
        Integer[] trip_ids = filterColumn(source.getIntColumn("trip_id"), count, filter);
        durations = filterColumn(source.getIntColumn("duration"), count, filter);
        String[] start_times = filterColumn(source.getColumn("start_time"), count, filter);
        String[] end_times = filterColumn(source.getColumn("end_time"), count, filter);
        Integer[] bike_ids = filterColumn(source.getIntColumn("bike_id"), count, filter);
        String[] bike_types = filterColumn(source.getColumn("bike_type"), count, filter);
        String[] trip_route_categories = filterColumn(source.getColumn("trip_route_category"), count, filter);
        Integer[] plan_durations = filterColumn(source.getIntColumn("plan_duration"), count, filter);
        String[] passholder_types = filterColumn(source.getColumn("passholder_type"), count, filter);
        String[] start_stations = filterColumn(source.getColumn("start_station"), count, filter);

        logger.info(String.format("Saving columns in \"%s\".", output));
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
        logger.info("Third transform finished.");
    }
}
