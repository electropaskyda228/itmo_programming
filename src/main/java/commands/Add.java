package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.models.Movie;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Add extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private CollectionManager collectionManager;
    private Movie movie;

    public Add(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("add", "Add new element", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setCommandManager(CommandManager commandManager) {
    }

    @Override
    public Response apply() {
        movie.setId(collectionManager.getFreeId());
        collectionManager.addElement(movie);
        return new Response("Movie has been added successfully");
    }
}
