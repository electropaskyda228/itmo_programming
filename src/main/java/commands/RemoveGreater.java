package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.models.Movie;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serializable;

public class RemoveGreater extends Command implements Serializable {

    private final Console console;
    private Movie movie;
    private CollectionManager collectionManager;

    public RemoveGreater(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("remove_greater {element}", "Remove all elements greater than specified", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        try {
            movie.setId(collectionManager.getFreeId());
            if (collectionManager.removeGreater(movie)) return new Response("All elements greater than specified hasn't been removed");
            return new Response("There is no element greater than specified");
        } catch (CloneNotSupportedException exception) {
            return new Response(false, "Error with cloning");
        }
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setCommandManager(CommandManager commandManager) {

    }
}
