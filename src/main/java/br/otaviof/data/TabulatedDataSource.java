package br.otaviof.data;

import br.otaviof.sort.Util;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabulatedDataSource {
    private final Logger logger = Logger.getLogger("TabulatedDataSource");
    private final String file;
    private final String separator;
    private final String[] headers;
    private final int lineCount;

    public String[] getHeaders() {
        return this.headers;
    }

    private BufferedReader getReader(boolean skipHeaders) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.file));
        if (skipHeaders)
            reader.readLine();
        return reader;
    }

    private String[] fetchHeaders() throws IOException {
        BufferedReader reader = getReader(false);
        String firstLine = reader.readLine();
        String[] result = firstLine.split(this.separator);
        reader.close();
        return result;
    }

    private int countLines() throws IOException {
        BufferedReader reader = getReader(true);
        int count = 0;
        while (reader.readLine() != null)
            count++;
        reader.close();
        return count;
    }

    public TabulatedDataSource(String file, char separator) {
        this.separator = String.valueOf(separator);
        this.file = file;
        try {
            this.headers = this.fetchHeaders();
            this.lineCount = this.countLines();
        } catch(IOException e) {
            throw new RuntimeException("Failed to read file.", e);
        }
    }

    /***
     * Searches for the specified column index
     * @param name the column name
     * @return the column index or -1 if if it was not found
     */
    private int findColumnIndex(String name) {
        for (int i = 0; i<this.headers.length; i++)
            if (this.headers[i].equals(name))
                return i;
        return -1;
    }

    /***
     * Fetches the Nth column from the file
     * @param reader the file reader
     * @param index number of the column
     * @return the Nth index column data
     * @throws IOException if the file could not be read
     * @throws IllegalArgumentException if the index is invalid
     */
    private String fetchColumn(BufferedReader reader, int index) throws IOException {
        if (index >= this.headers.length || index < 0)
            throw new IllegalArgumentException("Invalid index");
        String line = reader.readLine();
        String[] entries = line.split(this.separator);
        return entries[index];
    }

    /***
     * Reorders the elements in the column according to a order array
     * @param column the column to be reordered
     * @param newOrder an array of positions
     * @param <T> a type
     */
    private <T> void reorderColumn(T[] column, int[] newOrder) {
        for (int i=0; i<column.length; i++)
            Util.swap(column, newOrder[i], i);
    }

    /***
     * Get data from a single column
     * @param name the name of the column
     * @return Each row of the specified column, or an empty array if the column could not be found by the name specified
     * @throws IOException If the file cannot be read
     */
    public String[] getColumn(String name) throws IOException {
        int index = findColumnIndex(name);
        if (index==-1)
            return new String[] { };
        BufferedReader reader = getReader(true);
        String[] result = new String[this.lineCount];
        for (int i=0; i<this.lineCount; i++) {
            result[i] = fetchColumn(reader, index);
        }
        reader.close();
        return result;
    }

    /***
     * Get data from a single column in a specific order
     * @param name the name of the column
     * @param order the index of each row element
     * @return Each row of the specified column reordered or an empty array if the column could not be found by the name specified
     * @throws IOException If the file cannot be read
     */
    public String[] getColumn(String name, int[] order) throws IOException {
        String[] result = getColumn(name);
        reorderColumn(result, order);
        return result;
    }

    private static final Pattern DATE_REGEX = Pattern.compile(".*?(\\d+)[/-](\\d+)[/-](\\d\\d\\d\\d) (\\d+):(\\d\\d).*", Pattern.CASE_INSENSITIVE);
    private LocalDateTime parseDate(String text) {
        Matcher match = DATE_REGEX.matcher(text);
        if (!match.matches())
            throw new RuntimeException(String.format("Could not parse date from \"%s\"", text));
        int day = Integer.parseInt(match.group(1));
        int month = Integer.parseInt(match.group(2));
        int year = Integer.parseInt(match.group(3));
        int hour = Integer.parseInt(match.group(4));
        int minute = Integer.parseInt(match.group(5));
        //logger.info(String.format("Parsing date '%s', (day %d, month %d, year %d, hour %d, minute %d)", dates[i], day, month, year, hour, minute));
        if (month <= 12)
            return LocalDateTime.of(year, month, day, hour, minute);
        else
            return LocalDateTime.of(year, day, month, hour, minute);
    }

    public LocalDateTime[] getDateColumn(String name) throws IOException {
        // Colunas de dia e mes mto inconsistentes, LocalDateTimeFormatter n funciona
        // TODO: Data com regex. Enquanto n estiver tudo certo, mais regex
        String[] dates = getColumn(name);
        LocalDateTime[] result = new LocalDateTime[this.lineCount];
        for (int i=0; i<this.lineCount; i++)
            result[i] = parseDate(dates[i]);
        return result;
    }
    public LocalDateTime[] getDateColumn(String name, int[] order) throws IOException {
        LocalDateTime[] result = getDateColumn(name);
        reorderColumn(result, order);
        return result;
    }

    private static final Pattern INT_REGEX = Pattern.compile(".*?(\\d+).*", Pattern.CASE_INSENSITIVE);
    private Integer parseInteger(String text) {
        Matcher match = INT_REGEX.matcher(text);
        if (!match.matches())
            throw new RuntimeException(String.format("Could not parse integer from \"%s\"", text));
        return Integer.parseInt(match.group(1));
    }

    public Integer[] getIntColumn(String name) throws IOException {
        // Colunas de inteiros possuem caracteres soltos
        String[] values = getColumn(name);
        Integer[] result = new Integer[this.lineCount];
        for (int i=0; i<this.lineCount; i++)
            result[i] = parseInteger(values[i]);
        return result;
    }

    public Integer[] getIntColumn(String name, int[] order) throws IOException {
        Integer[] result = getIntColumn(name);
        reorderColumn(result, order);
        return result;
    }

    private String fetchLine(int i) throws IOException {
        BufferedReader br = getReader(true);
        while (i > 0) {
            br.readLine();
            i--;
        }
        String line = br.readLine();
        br.close();
        return line;
    }

    public void appendToLine(BufferedWriter writer, BufferedReader reader, String text) throws IOException {
        String line = (reader != null) ? reader.readLine() + this.separator : "";
        line += text;
        writer.write(line);
        writer.newLine();
    }

    public <T> void writeColumn(String target, String name, T[] values) throws IOException {
        String tempname = "000-"+target;
        //Files.deleteIfExists(Paths.get(target));
        Files.deleteIfExists(Paths.get(tempname));
        File file = new File(target);
        File temp = new File("000-"+target);
        BufferedReader reader = file.exists() ? new BufferedReader(new FileReader(file)) : null;
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
        // Write header
        appendToLine(writer, reader, name);
        // Write column
        for (T value : values) {
            appendToLine(writer, reader, value.toString());
        }
        writer.close();
        if (reader != null)
            reader.close();
        Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

}
