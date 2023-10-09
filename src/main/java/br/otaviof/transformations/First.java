package br.otaviof.transformations;

import br.otaviof.data.TabulatedDataSource;

import java.io.IOException;
import java.util.logging.Logger;

/***
 * First item, replaces the station id with its name
 */
public class First implements Transformation {
    private final TabulatedDataSource bikeDataSource;
    private final TabulatedDataSource stationDataSource;

    public First(String bikeData, String stationData) {
        this.bikeDataSource = new TabulatedDataSource(bikeData, ',');
        this.stationDataSource = new TabulatedDataSource(stationData, ',');
    }


    @Override
    public void transform(String output) throws IOException {
        Logger logger = Logger.getLogger("FirstTransformation");
        logger.info("Fetching data from csv.");
        String[] station_names = stationDataSource.getColumn("station_name");
        Integer[] station_ids = stationDataSource.getIntColumn("station_id");
        Integer[] start_station = bikeDataSource.getIntColumn("start_station");

        logger.info("Putting station names into 'start_station'.");
        String[] new_start_station = new String[start_station.length];
        for (int i=0; i<start_station.length; i++) {
            for (int j = 0; j < station_ids.length; j++) {
                if (start_station[i].equals(station_ids[j])) {
                    String name = station_names[j];
                    new_start_station[i] = name;
                }
            }
            // Fix missing station names
            if (new_start_station[i] == null) {
                new_start_station[i] = "Unknown";
            }
        }

        logger.info(String.format("Saving columns in \"%s\".", output));
        this.bikeDataSource.writeColumn(output, "trip_id", bikeDataSource.getIntColumn("trip_id"));
        this.bikeDataSource.writeColumn(output, "duration", bikeDataSource.getIntColumn("duration"));
        this.bikeDataSource.writeColumn(output, "start_time", bikeDataSource.getColumn("start_time"));
        this.bikeDataSource.writeColumn(output, "end_time", bikeDataSource.getColumn("end_time"));
        this.bikeDataSource.writeColumn(output, "bike_id", bikeDataSource.getIntColumn("bike_id"));
        this.bikeDataSource.writeColumn(output, "bike_type", bikeDataSource.getColumn("bike_type"));
        this.bikeDataSource.writeColumn(output, "trip_route_category", bikeDataSource.getColumn("trip_route_category"));
        this.bikeDataSource.writeColumn(output, "plan_duration", bikeDataSource.getIntColumn("plan_duration"));
        this.bikeDataSource.writeColumn(output, "passholder_type", bikeDataSource.getColumn("passholder_type"));
        this.bikeDataSource.writeColumn(output, "start_station", new_start_station);
        logger.info("First transform finished.");
    }
}
