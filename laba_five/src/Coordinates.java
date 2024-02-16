public class Coordinates {
    public float getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    private float x; //Максимальное значение поля: 238
    private Double y; //Максимальное значение поля: 112, Поле не может быть null

    public Coordinates(float x, Double y) {
        setX(x);
        setY(y);
    }
}
