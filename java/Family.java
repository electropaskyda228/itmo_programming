package main.java;

import java.util.ArrayList;
import java.util.List;

public class Family {
    private List<FamilyMember> family;

    Family() {
        family = new ArrayList<>();
    }

    public void addFamilyMember(FamilyMember person) {
        family.add(person);
    }

    public List<FamilyMember> getFamily() {
        return family;
    }

    public void printFamily() {
        for (FamilyMember person : family) {
            System.out.println(person);
        }
    }

    public void doLove(String text) {
        for (FamilyMember person : family) {
            person.love(text);
        }
    }
}
