package main.java.models;

public enum Country {
    INDIA("India"),
    VATICAN("Vatican"),
    JAPAN("Japan");

    private String name;

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
