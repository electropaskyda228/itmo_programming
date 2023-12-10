package main.java;

public enum DayTime{
    MORNING("утро"), AFTERNOON("полдень"), EVENING("вечер"), NIGHT("ночь");
    private String description;
    DayTime(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
}
