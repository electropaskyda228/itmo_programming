package main.java;

public class FamilyFriend extends Citizen {
    FamilyFriend(String name, boolean male) {
        super(name, male);
        this.type = TypeCreature.FAMILY_FRIEND;
    }
    public void helpFamily(String text){
        mood = Mood.FRIENDLY;
        System.out.print(text);
    }
}
