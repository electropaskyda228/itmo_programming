package main.java;

class NotEnoughPower extends Exception{
    @Override
    public String getMessage(){
        return "\n $недостаточно сил$ \n";
    }
}
