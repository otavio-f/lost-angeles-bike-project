package br.otaviof.transformations;

import br.otaviof.data.TabulatedDataSource;

import java.io.IOException;
import java.util.logging.Logger;

import static br.otaviof.transformations.Util.filterColumn;

/***
 * Second transformation, filters by stations located on Pasadena
 */
public class Second implements Transformation {
    private final TabulatedDataSource bikeSource;
    private final TabulatedDataSource stationSource;

    public Second(String bikeSource, String stationSource) {
        this.bikeSource = new TabulatedDataSource(bikeSource, ',');
        this.stationSource = new TabulatedDataSource(stationSource, ',');
    }

    @Override
    public void transform(String output) throws IOException {
        Logger logger = Logger.getLogger("SecondTransform");

        logger.info("Fetching data from csv.");
        String[] stations = bikeSource.getColumn("start_station");
        String[] station_names = stationSource.getColumn("station_name");
        String[] locations = stationSource.getColumn("location");

        logger.info("Selecting stations on Pasadena.");
        boolean[] filter = new boolean[stations.length];
        int count = 0;
        for (int i=0; i<filter.length; i++) {
            filter[i] = false;
            String station = stations[i];
            for (int j=0; j<locations.length; j++) {
                String location = locations[j];
                String refStation = station_names[j];
                if (location.equalsIgnoreCase("pasadena"))
                    if (station.equals(refStation)) {
                        filter[i] = true;
                        count++;
                        break;
                }
            }
        }

        logger.info("Gathering records located on Pasadena");
        Integer[] trip_ids = filterColumn(bikeSource.getIntColumn("trip_id"), count, filter);
        Integer[] durations = filterColumn(bikeSource.getIntColumn("duration"), count, filter);
        String[] start_times = filterColumn(bikeSource.getColumn("start_time"), count, filter);
        String[] end_times = filterColumn(bikeSource.getColumn("end_time"), count, filter);
        Integer[] bike_ids = filterColumn(bikeSource.getIntColumn("bike_id"), count, filter);
        String[] bike_types = filterColumn(bikeSource.getColumn("bike_type"), count, filter);
        String[] trip_route_categories = filterColumn(bikeSource.getColumn("trip_route_category"), count, filter);
        Integer[] plan_durations = filterColumn(bikeSource.getIntColumn("plan_duration"), count, filter);
        String[] passholder_types = filterColumn(bikeSource.getColumn("passholder_type"), count, filter);
        String[] start_stations = filterColumn(bikeSource.getColumn("start_station"), count, filter);

        logger.info(String.format("Saving columns in \"%s\".", output));
        this.bikeSource.writeColumn(output, "trip_id", trip_ids);
        this.bikeSource.writeColumn(output, "duration", durations);
        this.bikeSource.writeColumn(output, "start_time", start_times);
        this.bikeSource.writeColumn(output, "end_time", end_times);
        this.bikeSource.writeColumn(output, "bike_id", bike_ids);
        this.bikeSource.writeColumn(output, "bike_type", bike_types);
        this.bikeSource.writeColumn(output, "trip_route_category", trip_route_categories);
        this.bikeSource.writeColumn(output, "plan_duration", plan_durations);
        this.bikeSource.writeColumn(output, "passholder_type", passholder_types);
        this.bikeSource.writeColumn(output, "start_station", start_stations);
        logger.info("Second transform finished.");
    }
}
