package main.java;

public enum StatusWaterPlace {
    CALM("штиль"), SERIOUS_CALM("полный штиль"), STORM("шторм");
    private String description;
    StatusWaterPlace(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
