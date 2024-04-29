package main.java.utility;

public class CSVReader {
    private final String delimiter;
    private String[] csvRows;
    private int index;

    public CSVReader(String delimiter) {
        this.delimiter = delimiter;
        this.index = -1;
    }

    public String[] readNextLine() {
        index += 1;
        return csvRows[index].split(delimiter);
    }

    public boolean hasNextLine() {
        return (index + 1) < csvRows.length;
    }

    public void setCsvRow(String row) {
        csvRows = row.split("\n");
    }

    public String getCsvRow() {
        return String.join("\n", csvRows);
    }
}
