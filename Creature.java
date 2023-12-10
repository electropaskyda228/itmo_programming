package main.java;

public abstract class Creature extends Entity {
    protected TypeCreature type;

    public abstract void eating();

    public abstract void dying();

    public abstract void see(String text);

    public Creature(String name) {
        super(name);
        this.type = TypeCreature.CREATURE;
    }


    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null || object.getClass() != this.getClass()) return false;
        Creature otherCreature = (Creature) object;
        return otherCreature.getType().equals(type) && otherCreature.name.equals(name);
    }

    public TypeCreature getType() {
        return type;
    }
}
