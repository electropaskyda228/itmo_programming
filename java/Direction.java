package main.java;

public enum Direction {
    FORWARD("вперед"), LEFT("влево"), RIGHT("вправо"), BACK("назад");

    private String description;

    Direction(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
