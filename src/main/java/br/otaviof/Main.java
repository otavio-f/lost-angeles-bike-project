package br.otaviof;

import br.otaviof.data.ConfigReader;
import br.otaviof.data.TabulatedDataSource;
import br.otaviof.transformations.First;
import br.otaviof.transformations.Second;
import br.otaviof.transformations.Third;
import br.otaviof.sort.methods.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class Main {
    final static Logger logger = Logger.getLogger("Main");

    public static <T extends Comparable<T>> double Benchmark(Sorter method, T[] data) {
        long startTime = System.nanoTime();
        //method.sort(Arrays.copyOf(data, data.length), false);
        method.sort(data, false);
        return (System.nanoTime()-startTime)/1_000_000_000.0;
        //TODO: Save column
    }

    private static void orderBy(String source, String column) {

    }

    private static void orderReverseBy(String source, String column) {

    }

    public static void main(String[] args) {
        try {
            ConfigReader conf = new ConfigReader("/config.txt");
            String[] lines = conf.getLines();
            final String target = "LAMetroTrips.csv";
            //new First(lines[0], lines[1]).transform(target);
            //new Second(target, lines[1]).transform("LAMetroTrips_F1.csv");
            new Third(target).transform("LAMetroTrips_F2.csv");
            orderBy(target, "station_name");
            orderBy(target, "duration");
            orderReverseBy(target, "start_time");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main2(String[] args) {
        try {
            ConfigReader conf = new ConfigReader("/config.txt");
            String[] lines = conf.getLines();
            TabulatedDataSource ts = new TabulatedDataSource(lines[0], ',');

            logger.info(String.format("Gathering data from file %s.", lines[0]));
            // String[] original = ts.getColumn("start_time");
            LocalDateTime[] original = ts.getDateColumn("start_time");
            //Integer[] original = ts.getIntColumn("plan_duration");
            logger.info(String.format("Found %d records and the first is \"%s\"", original.length, original[0]));

            logger.info("Sorting data.");
            double time = Benchmark(new Selection(), original);

            logger.info(String.format("Took %.2fs to sort.", time));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}