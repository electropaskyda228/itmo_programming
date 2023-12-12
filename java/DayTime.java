package main.java;

public enum DayTime{
    MORNING("утро"), AFTERNOON("полдень"), EVENING("вечер"), NIGHT("ночь"), NOW("сейчас");
    private String description;
    DayTime(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
}
