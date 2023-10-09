package br.otaviof.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

public class ConfigReader {
    private final String file;
    public ConfigReader(String config) {
        URL url = this.getClass().getResource(config);
        this.file = url.getFile();
    }

    private int getLineCount() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.file));
        int count = 0;
        while (br.readLine() != null)
            count++;
        return count;
    }

    public String[] getLines() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.file));
        String[] content = new String[getLineCount()];
        for (int i=0; i<content.length; i++) {
            content[i] = br.readLine();
        }
        br.close();
        return content;
    }
}
