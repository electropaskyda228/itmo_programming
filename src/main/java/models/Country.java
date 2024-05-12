package main.java.models;

import java.io.Serial;
import java.io.Serializable;

public enum Country implements Serializable {
    INDIA("India"),
    VATICAN("Vatican"),
    JAPAN("Japan");

    private final String name;
    @Serial
    private static final long serialVersionUID = 1L;

    Country(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Country getFromString(String name) {
        return switch(name.toLowerCase()) {
            case "india" -> INDIA;
            case "vatican" -> VATICAN;
            case "japan" -> JAPAN;
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };
    }

}
