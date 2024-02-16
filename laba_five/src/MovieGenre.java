public enum MovieGenre {
    ACTION,
    MUSICAL,
    ADVENTURE,
    SCIENCE_FICTION;

    public static MovieGenre getFromString(String name) {
        return switch(name.toLowerCase()) {
            case "action" -> ACTION;
            case "musical" -> MUSICAL;
            case "adventure" -> ADVENTURE;
            case "science fiction" -> SCIENCE_FICTION;
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };
    }
}