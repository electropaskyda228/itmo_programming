package main.java.models;

import java.io.Serial;
import java.io.Serializable;

public record User(String name, String password) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public boolean validate() {
        return !(password.isEmpty() || password.length() > 27);
    }
}
