package main.java.utility;

public class CSVWriter {
    private final String delimiter;
    private String csvRow = "";

    public CSVWriter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void writeNext(isCSVImaginable element) {
        csvRow += String.join(delimiter, element.toArrayString()) + "\n";
    }

    public void writeColumnsName(String[] columns) {
        csvRow += String.join(delimiter, columns) + "\n";
    }

    public String getCsvRow() {
        return csvRow;
    }
}
