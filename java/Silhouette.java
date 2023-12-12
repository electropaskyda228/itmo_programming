package main.java;

public class Silhouette extends Entity {
    private Entity base;
    Silhouette(Entity base) {
        super("силуэт");
        this.base = base;
    }
    public Entity getBase(){
        return base;
    }
    public String toString(){
        return getAdjectives() + name + " " + base.toString();
    }
}
