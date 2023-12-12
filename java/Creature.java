package main.java;

import java.nio.file.FileAlreadyExistsException;

public abstract class Creature extends Entity {
    protected TypeCreature type;

    protected int power;

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
    public void changePower(int delta){
        power += delta;
        if(power < 0){
            power = 0;
        }
    }
    public boolean makeAnEffort(int needPower){
        try{
            if(needPower <= this.power){
                power -= needPower;
                return true;
            } else{
                throw new NotEnoughPower();
            }
        } catch (NotEnoughPower e) {
            System.out.print(e.getMessage());
            return false;
        }
    }
}


