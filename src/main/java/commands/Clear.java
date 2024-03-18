package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "Clear collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        collectionManager.clear();
        return new Response("Collection are cleared");
    }
}
