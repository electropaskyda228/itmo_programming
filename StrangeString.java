package main.java;

public class StrangeString {
    static String makeFirst(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
