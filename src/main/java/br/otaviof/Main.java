package br.otaviof;

import br.otaviof.data.ConfigReader;
import br.otaviof.data.TabulatedDataSource;
import br.otaviof.transformations.First;
import br.otaviof.transformations.Fourth;
import br.otaviof.transformations.Second;
import br.otaviof.transformations.Third;
import br.otaviof.sort.methods.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class Main {
    final static Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {
        try {
            ConfigReader conf = new ConfigReader("/config.txt");
            String[] lines = conf.getLines();
            final String target = "LAMetroTrips.csv";
            new First(lines[0], lines[1]).transform(target);
            new Second(target, lines[1]).transform("LAMetroTrips_F1.csv");
            new Third(target).transform("LAMetroTrips_F2.csv");
            new Fourth(target).transform("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}