package main.java;

public class SentenceMaker {
    public static String dependentConnection(boolean sex, boolean many, boolean between, String first, String second){
        String answer = first;
        if(many){
            answer += ", которые ";
        } else if(sex){
            answer += ", который ";
        } else if(between){
            answer += ", которое ";
        } else {
            answer += ", которая ";
        }
        return answer + second;
    }
    public static String bunchSentences(String first, String second){
        return first + ", и вот лишь " + second;
    }
}
