package main.java;

import java.util.ArrayList;
import java.util.List;

public class Tube extends Entity {
    Tube(String name) {
        super(name);
    }
    class CircleSmoke{
        public void flight(String text){
            System.out.println(text);
        }
    }
    public void throughCircles(int quantity){
        List<CircleSmoke> smoke = new ArrayList<>(quantity);
        for(int i=0; i<quantity; i++){
            CircleSmoke circle = new CircleSmoke();
            smoke.add(circle);
            circle.flight((i + 1) + " кольцо вспархнуло к небу.");
        }
    }
}
