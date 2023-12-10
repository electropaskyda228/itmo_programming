package main.java;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    protected String name;
    protected List<String> adjectives = new ArrayList<>();
    Entity(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void changeName(String name){
        this.name = name;
    }
    public String toString(){
        return getAdjectives() + name;
    }
    public void addAdjective(String word){
        adjectives.add(word);
    }
    public String getAdjectives(){
        StringBuilder result = new StringBuilder();
        for(String word: adjectives) result.append(word).append(" ");
        return result.toString();
    }
}
