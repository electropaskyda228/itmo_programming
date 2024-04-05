package main.java.utility;

import main.java.models.MpaaRating;

public abstract class Element implements Comparable<Element>, Validatable, isCSVImaginable {
    abstract public MpaaRating getMpaaRating();
}
