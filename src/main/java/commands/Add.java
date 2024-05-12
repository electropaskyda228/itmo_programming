package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.Movie;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Add extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private Movie movie;

    public Add(Console console) {
        super("add", "Add new element");
        this.console = console;
    }

    @Override
    public Response apply() {
        CollectionManager collectionManager = CollectionManager.getInstance();
        movie.setId(collectionManager.getFreeId());
        collectionManager.addElement(movie);
        return new Response("Movie has been added successfully");
    }
}
