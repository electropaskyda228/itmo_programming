package main.java;

public enum PowerOfAction {
    ALL_WEAKER("все слабее"), ALL_WEAKER_AND_WEAKER("все слабее и слабее"), STRONGER("сильнее"), STRONG("сильно"),
    WEAK("слабо"), ALL_STRONGER("все сильнее");

    private String description;

    PowerOfAction(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
