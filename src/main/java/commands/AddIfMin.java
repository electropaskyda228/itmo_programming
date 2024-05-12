package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.Movie;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class AddIfMin extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private Movie movie;
    public AddIfMin(Console console) {
        super("add_if_min", "Add element if its value less than the least element of collection");
        this.console = console;
    }

    @Override
    public Response apply() {
        CollectionManager collectionManager = CollectionManager.getInstance();
        movie.setId(collectionManager.getFreeId());
        if (collectionManager.containPassportId(movie.getOperator().getPassportID())) {
            collectionManager.makeDecId();
            return new Response(false, "Element with that passport id already exist");
        }
        if (collectionManager.checkElementLessMinimum(movie)) {
            collectionManager.addElement(movie);
            return new Response("Movie has been added successfully");
        }
        collectionManager.makeDecId();
        return new Response(false, "Element is bigger than the least of collection. Element hasn't been added.");

    }
}
