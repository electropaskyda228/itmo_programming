package main.java.utility;

import main.java.models.MpaaRating;

import java.io.Serial;
import java.io.Serializable;

public abstract class Element implements Comparable<Element>, Validatable, isCSVImaginable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    abstract public MpaaRating getMpaaRating();
}
