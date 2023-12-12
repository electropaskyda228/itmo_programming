package main.java;

public enum Duration {
    FAST("быстро"), LONGTIME("долгое время"), LONG("долго");

    private String description;

    Duration(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
