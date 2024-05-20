package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.DumpManager;
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
        if (collectionManager.containPassportId(movie.getOperator().getPassportID())) {
            return new Response(false, "Element with that passport id already exist");
        }
        long movieId = DumpManager.getInstance().addElement(getUser(), movie);
        if (movieId == -1) return new Response(false, "Movie hasn't been added");
        movie.setId(movieId);
        collectionManager.addElement(movie);
        return new Response("Movie has been added successfully");
    }
}
