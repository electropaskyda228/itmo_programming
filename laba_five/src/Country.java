public enum Country {
    INDIA,
    VATICAN,
    JAPAN;

    public static Country getFromString(String name) {
        return switch(name.toLowerCase()) {
            case "india" -> INDIA;
            case "vatican" -> VATICAN;
            case "japan" -> JAPAN;
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };
    }
}
