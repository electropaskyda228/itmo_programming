package org.example.lab3;

public record PointResult(Double x, Double y, Double r, boolean result) {
    public String getX() {
        return Double.toString(x);
    }
    public String getY() {
        return Double.toString(y);
    }
    public String getR() {
        return Double.toString(r);
    }
    public boolean isResult() {
        return result;
    }
}
