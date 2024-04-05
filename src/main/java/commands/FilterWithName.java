package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

public class FilterWithName extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public FilterWithName(Console console, CollectionManager collectionManager) {
        super("filter_starts_with_name name", "Show elements which names start with specified");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (arguments.length != 2) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        return new Response(collectionManager.filterStartsWithName(arguments[1]));
    }
}
