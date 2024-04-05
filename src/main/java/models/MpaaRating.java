package main.java.models;

public enum MpaaRating {
    G("G"),
    PG("PG"),
    PG_13("PG 13"),
    R("R"),
    NC_17("NC 17");

    private String name;
    private static String[] orderMpaa = {"G", "PG", "PG 13", "R", "NC 17"};

    MpaaRating(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static MpaaRating getFromString(String name) {
        return switch(name.replace(" ", "").toLowerCase()) {
            case "g" -> G;
            case "pg" -> PG;
            case "pg13" -> PG_13;
            case "r" -> R;
            case "nc17" -> NC_17;
            default -> throw new IllegalStateException("Unexpected value: " + name.toLowerCase());
        };
    }

    public static int compareTo(MpaaRating rating, MpaaRating other) {
        return getMpaaRatingInt(rating) - getMpaaRatingInt(other);
    }

    public static int getMpaaRatingInt(MpaaRating rating) {
        String ratingString = rating.toString();
        for (int i=0; i<orderMpaa.length; i++) {
            if (ratingString.equals(orderMpaa[i])) return i;
        }
        return -1;
    }

    public static MpaaRating getMpaaRatingByInt(Integer rating) {
        return getFromString(orderMpaa[rating]);
    }
}

