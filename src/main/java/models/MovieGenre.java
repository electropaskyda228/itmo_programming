package main.java.models;

public enum MovieGenre {
    ACTION("action"),
    MUSICAL("musical"),
    ADVENTURE("adventure"),
    SCIENCE_FICTION("science fiction");

    private String name;

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