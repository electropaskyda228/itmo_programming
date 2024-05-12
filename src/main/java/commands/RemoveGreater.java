package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.Movie;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serializable;

public class RemoveGreater extends Command implements Serializable {

    private final Console console;
    private Movie movie;

    public RemoveGreater(Console console) {
        super("remove_greater {element}", "Remove all elements greater than specified");
        this.console = console;
    }

    @Override
    public Response apply() {
        try {
            CollectionManager collectionManager = CollectionManager.getInstance();
            movie.setId(collectionManager.getFreeId());
            if (collectionManager.removeGreater(movie)) return new Response("All elements greater than specified hasn't been removed");
            return new Response("There is no element greater than specified");
        } catch (CloneNotSupportedException exception) {
            return new Response(false, "Error with cloning");
        }
    }
}
