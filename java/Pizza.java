package main.java;

public class Pizza extends Entity{
    private int pieces = 1;
    Pizza(String name) {
        super(name);
    }
    public void changePieces(int newPieces) throws ZeroPieces{
        if(newPieces == 0){
            throw new ZeroPieces();
        }
        pieces = newPieces;
    }
    public int getPieces(){
        return pieces;
    }
}
