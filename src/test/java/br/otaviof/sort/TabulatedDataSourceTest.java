package br.otaviof.sort;

import br.otaviof.data.TabulatedDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class TabulatedDataSourceTest {

    @Test
    public void testGetColumn() throws IOException {
        URL testFile = this.getClass().getResource("/sample.txt");
        TabulatedDataSource td = new TabulatedDataSource(testFile.getFile(), ',');

        String[] expected = {"Year", "1997", "2000"};
        String[] result = td.getColumn("Year");
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void testWriteColumn() throws IOException {
    }
}
