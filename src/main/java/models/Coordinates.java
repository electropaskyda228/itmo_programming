package main.java.models;

import main.java.utility.Validatable;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates implements Validatable, Serializable {
    public Integer getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    private Integer x; //Максимальное значение поля: 238
    private Double y; //Максимальное значение поля: 112, Поле не может быть null
    @Serial
    private static final long serialVersionUID = 1L;

    public Coordinates(Integer x, Double y) {
        setX(x);
        setY(y);
    }

    @Override
    public String toString() {
        return getX() + " " + getY();
    }

    public static Coordinates getFromString(String data, String delimiter) {
        String[] line = data.split(delimiter);
        return new Coordinates(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
    }

    @Override
    public boolean validate() {
        if (x == null || x > 238) return false;
        return y != null && y <= 112;
    }
}
