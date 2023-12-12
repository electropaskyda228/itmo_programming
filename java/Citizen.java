package main.java;

import java.util.ArrayList;
import java.util.List;

public class Citizen extends Creature implements Thinkable {
    protected final boolean male;
    protected List<Entity> stuff = new ArrayList<>();
    protected Mood mood = Mood.NOTHING;

    Citizen(String name, boolean male) {
        super(name);
        this.male = male;
        this.type = TypeCreature.CITIZEN;
    }

    @Override
    public void eating() {
        System.out.print("мирно " + (male ? "ел" : "ела"));
    }

    @Override
    public void dying() {
        System.out.print("грустно " + (male ? "умер" : "умерла"));
    }

    @Override
    public void see(String text) {
        System.out.println((male ? "смотрел" : "смотрела") + " на " + text);
    }

    @Override
    public void justThink(String text) {
        mood = Mood.PENSIVE;
        System.out.print(text);
    }

    @Override
    public void smartThink(String text) {
        mood = Mood.SMART;
        System.out.print(text);
    }

    @Override
    public void philosophicallyThink(String text) {
        mood = Mood.PHILOSOPHICAL;
        System.out.print(text);
    }

    public void row(int power) {
        if(!makeAnEffort(power)){
            return;
        };
        mood = Mood.SPORT;
        System.out.print(male ? "греб" : "гребла");
    }

    public boolean getMale() {
        return male;
    }

    public List<Entity> getStuff() {
        return this.stuff;
    }

    public void addStuff(Entity stuff) {
        this.stuff.add(stuff);
    }

    enum Mood {
        ANGRY, LOVE, PHILOSOPHICAL, PENSIVE, SMART, SPORT, NOTHING, FRIENDLY
    }

    public void changeMood(Mood mood) {
        this.mood = mood;
    }


}
