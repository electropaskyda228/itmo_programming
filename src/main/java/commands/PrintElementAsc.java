package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

public class PrintElementAsc extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintElementAsc(Console console, CollectionManager collectionManager) {
        super("print_field_ascending_mpaa_rating", "Show ascending fields mpaa rating of all elements");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        return new Response(collectionManager.getAllMpaarating());
    }
}
