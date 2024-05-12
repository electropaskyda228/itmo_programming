package main.java.managers;

import main.java.models.Movie;
import main.java.utility.CSVReader;
import main.java.utility.CSVWriter;
import main.java.utility.Console;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.TreeSet;

public class DumpManager {
    private String fileName;
    private Console console;
    private final String[] columns = {"ID", "name", "coordinates", "creation date", "oscars count", "golden palm count",
            "genre", "mpaa rating", "operator's name", "operator's birthday",
            "operator's weight", "operator's passportID", "operator's nationality"};

    private DumpManager() {
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private static class DumpManagerHolder{
        private static final DumpManager HOLDER_INSTANCE = new DumpManager();
    }

    public static DumpManager getInstance() {
        return DumpManagerHolder.HOLDER_INSTANCE;
    }

    private String collectionToCSV(Collection<Movie> collection) {
        try {
            CSVWriter writer = new CSVWriter(";");
            writer.writeColumnsName(columns);
            for(var element: collection) {
                writer.writeNext(element);
            }
            return writer.getCsvRow();
        } catch (Exception e) {
            console.printError("Serialization error");
            return null;
        }
    }

    public boolean writeCollectionToCSV(Collection<Movie> collection) {
        BufferedOutputStream bos = null;
        try {
            var csvRow = collectionToCSV(collection);
            if(csvRow == null) return false;
            byte[] csvBytes = csvRow.getBytes(StandardCharsets.UTF_8);
            bos = new BufferedOutputStream(new FileOutputStream(fileName));

            try {
                bos.write(csvBytes);
                bos.flush();
            } catch (IOException e) {
                console.printError("Saving error");
                return false;
            }

        } catch (FileNotFoundException | NullPointerException e) {
            console.printError("File wasn't found");
            return false;
        } finally {
            try {
                assert bos != null;
                bos.close();
            } catch (Exception e) {
                console.println("File wasn't closed");
            }
        }
        return true;
    }

    private TreeSet<Movie> CSVToCollection(String s) {
        try {
            CSVReader reader = new CSVReader(";");
            reader.setCsvRow(s);
            TreeSet<Movie> collection = new TreeSet<>();
            String[] order = (reader.hasNextLine()) ? reader.readNextLine() : columns;
            String[] record;
            while (reader.hasNextLine()) {
                record = reader.readNextLine();
                Movie movie = Movie.fromArrayString(record, order);
                if (movie.validate())
                    collection.add(movie);
                else
                    console.printError("File with collection has invalid data");
            }
            return collection;
        } catch (Exception e) {
            console.printError("Deserialization error");
            return null;
        }
    }

    public void readCollection(Collection<Movie> collection) {
        if (fileName != null && !fileName.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
                var result = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    result.append(line).append("\n");
                    line = reader.readLine();
                }
                collection.clear();
                var data = CSVToCollection(result.toString());
                if(data != null) collection.addAll(data);
                if (!collection.isEmpty()) {
                    console.println("Collection has been loaded successfully");
                } else {
                    console.printError("In file need collection hasn't been detected");
                }
            } catch (FileNotFoundException exception) {
                console.printError("Load file hasn't been found");
            } catch (IOException ignored) {
                console.printError("Reading file error");
            }
        } else {
            console.printError("Boot file command line argument hasn't been found");
        }
    }
}
