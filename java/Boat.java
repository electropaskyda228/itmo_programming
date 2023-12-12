package main.java;

public class Boat extends Entity{
    Boat(String name) {
        super(name);
    }
    public void startToCircle(){
        System.out.print("стала описывать полукруг");
    }
}
