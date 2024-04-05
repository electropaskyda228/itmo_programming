package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", "Show all elements");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        try{
            return new Response(collectionManager.toString());
        } catch (Exception e) {
            return new Response(false, "Collection can't be shown");
        }
    }
}
