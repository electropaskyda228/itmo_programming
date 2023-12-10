import ru.ifmo.se.pokemon.*;

public class LabaSecond {
    public static void main(String[] args){
        Bulbasaur p1 = new Bulbasaur("Высоковольтный майонез", 36);
        Ivysaur p2 = new Ivysaur("Подводный Б0Х", 50);
        Venusaur p3 = new Venusaur("Грибочек мщения", 58);
        Furret p4 = new Furret("Скользкий тип", 56);
        Abomasnow p5 = new Abomasnow("Ледовый задира", 56);
        Smoochum p6 = new Smoochum("Девочка с сюрпризом", 48);
        Battle battle = new Battle();
        battle.addAlly(p1);
        battle.addAlly(p2);
        battle.addAlly(p3);
        battle.addFoe(p4);
        battle.addFoe(p5);
        battle.addFoe(p6);
        battle.go();
    }
}

class ThunderShock extends SpecialMove {
    ThunderShock() {
        super(Type.ELECTRIC, 40, 100);
    }

    protected void applyOppEffects(Pokemon p) {
        Effect checkEffect = new Effect().chance(0.1);
        if(checkEffect.success()) {
            Effect.paralyze(p);
        }
    }
    protected String describe(){ return "жестко играет песню AC/DC"; }
}
class DoubleEdge extends PhysicalMove {
    DoubleEdge() {
        super(Type.NORMAL, 120, 100);
    }
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().turns(0).stat(Stat.HP, 15));
    }
    protected String describe(){ return "атакует с проблемами"; }
}
class ZapCannon extends SpecialMove {
    ZapCannon() {
        super(Type.ELECTRIC, 120, 50);
    }
    protected void applyOppEffects(Pokemon opp) {
        Effect.paralyze(opp);
    }
    protected String describe(){ return "бьет камерой по голове"; }
}
class Amnesia extends StatusMove {
    Amnesia() {
        super(Type.PSYCHIC, 0, 100);
    }
    protected void applySelfEffects(Pokemon p){
        p.addEffect(new Effect().turns(0).stat(Stat.SPECIAL_DEFENSE, 2));
    }
    protected String describe(){ return "забыл, за чем он сюда пришел"; }
}
class SuperFang extends PhysicalMove {
    SuperFang() {
        super(Type.NORMAL, 0, 90);
    }
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().turns(0).stat(Stat.HP, -10));
    }
    protected String describe(){ return "наносит невероятный удар"; }
}
class Assist extends StatusMove {
    Assist() {
        super(Type.NORMAL, 0, 100);
    }
    protected void applyOppEffects(Pokemon p){
        int choice = (int) (Math.random() * 3);
        switch (choice){
            case 0 -> Effect.paralyze(p);
            case 1 -> Effect.burn(p);
        }
    }
    protected String describe(){ return "надеется на удачу"; }
}
class Encore extends StatusMove {
    Encore() {
        super(Type.NORMAL, 0, 100);
    }
    protected void applyOppEffects(Pokemon p){
        Effect.confuse(p);
    }
    protected String describe(){ return "пугает соперника и зрителей"; }
}
class MetalClaw extends PhysicalMove {
    MetalClaw() {
        super(Type.STEEL, 50, 95);
    }
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().turns(0).chance(0.1).stat(Stat.ATTACK, 1));
    }
    protected String describe(){ return "атакует металлическим клыком"; }
}
class SwordsDance extends StatusMove {
    SwordsDance() {
        super(Type.NORMAL, 0, 100);
    }
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().turns(0).stat(Stat.ATTACK, 2));
    }
    protected String describe(){ return "танцует с мечами (как же он хорош)"; }
}

class AeroBlast extends SpecialMove {
    AeroBlast() {
        super(Type.FLYING, 100, 95);
    }
    protected double calcCriticalHit(Pokemon paramPokemon1, Pokemon paramPokemon2) {
        if (paramPokemon1.getStat(Stat.SPEED) / 128.0D > Math.random()) {
            System.out.println("critical");
            return 2.0D;
        }
        return 1.0D;
    }
    protected String describe(){ return "решил, что время не ждет и надо использовать ульту, поэтому " +
            "применил Aero Blast"; }
}
class DragonRage extends SpecialMove {
    DragonRage() {
        super(Type.DRAGON, 0, 100);
    }
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().turns(0).stat(Stat.HP, -40));
    }
    protected String describe(){ return "наносит точный удар"; }
}
class Wish extends StatusMove {
    Wish() {
        super(Type.NORMAL, 0, 100);
    }
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().turns(0).chance(0.5).stat(Stat.HP, 20));
    }
    protected String describe(){ return "желает вам и себе здоровья"; }
}

class Bulbasaur extends Pokemon {
    Bulbasaur(String name, int level) {
        super(name, level);
        addType(Type.GRASS);
        addType(Type.POISON);
        setStats(45.0, 49.0, 49.0, 65.0, 65.0, 45.0);
        setMove(new ThunderShock(), new DoubleEdge());
    }
}

class Ivysaur extends Bulbasaur {
    Ivysaur(String name, int level) {
        super(name, level);
        setStats(60, 62, 63, 80, 80, 60);
        addMove(new ZapCannon());
    }
}

class Venusaur extends Ivysaur {
    Venusaur(String name, int level) {
        super(name, level);
        setStats(80, 82, 83, 100, 100, 80);
        addMove(new Amnesia());
    }
}

class Furret extends Pokemon {
    Furret(String name, int level) {
        super(name, level);
        addType(Type.NORMAL);
        setStats(85, 76, 64, 45, 55, 90);
        setMove(new SuperFang(), new Assist(), new Encore(), new MetalClaw());
    }
}

class Abomasnow extends Pokemon {
    Abomasnow(String name, int level) {
        super(name, level);
        addType(Type.GRASS);
        addType(Type.ICE);
        setStats(90, 92, 75, 92, 85, 60);
        setMove(new SwordsDance(), new AeroBlast(), new DragonRage());
    }
}

class Smoochum extends Pokemon {
    Smoochum(String name, int level) {
        super(name, level);
        addType(Type.ICE);
        addType(Type.PSYCHIC);
        setStats(45, 30, 15, 85, 65, 65);
        setMove(new SwordsDance(), new AeroBlast(), new DragonRage(), new Wish());
    }
}
