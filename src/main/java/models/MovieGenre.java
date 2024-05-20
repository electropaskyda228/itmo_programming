package main.java.models;

import java.io.Serial;
import java.io.Serializable;

public enum MovieGenre implements Serializable {
    ACTION("action"),
    MUSICAL("musical"),
    ADVENTURE("adventure"),
    SCIENCE_FICTION("science fiction");

    private final String name;
    @Serial
    private static final long serialVersionUID = 1L;

    MovieGenre(String name) {
        this.name = name;
    }

    public static MovieGenre getFromString(String name) {
        return switch(name.replace(" ", "").toLowerCase()) {
            case "action" -> ACTION;
            case "musical" -> MUSICAL;
            case "adventure" -> ADVENTURE;
            case "sciencefiction" -> SCIENCE_FICTION;
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };
    }

    @Override
    public String toString() {
        return this.name;
    }
}