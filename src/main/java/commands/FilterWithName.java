package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class FilterWithName extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private String movieName;
    private CollectionManager collectionManager;

    public FilterWithName(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("filter_starts_with_name name", "Show elements which names start with specified", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        return new Response(collectionManager.filterStartsWithName(this.movieName));
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setCommandManager(CommandManager commandManager) {

    }
}
