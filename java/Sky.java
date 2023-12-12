package main.java;

public class Sky extends Entity {
    Sky(String name) {
        super(name);
    }
    public String getFone(){
        return "фон " + this;
    }
}
