package main.java;

class ZeroPieces extends RuntimeException{
    public String getMessage(){
        return "\n $!НЕ ДЕЛИ НА НОЛЬ!$ \n";
    }
}
