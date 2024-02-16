public enum MpaaRating {
    G,
    PG,
    PG_13,
    R,
    NC_17;

    public static MpaaRating getFromString(String name) {
        return switch(name.toLowerCase()) {
            case "g" -> G;
            case "pg" -> PG;
            case "pg 13" -> PG_13;
            case "r" -> R;
            case "nc 17" -> NC_17;
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };
    }
}

