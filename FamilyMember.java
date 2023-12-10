package main.java;

public class FamilyMember extends Citizen implements Loveable {
    FamilyMember(String name, boolean male){
        super(name, male);
        this.type = TypeCreature.FAMILY_MEMBER;
    }
    public void love(String text){
        mood = Mood.LOVE;
        System.out.print(text);
    }

    @Override
    public void hate(String text) {
        mood = Mood.ANGRY;
        System.out.print(text);
    }
}
