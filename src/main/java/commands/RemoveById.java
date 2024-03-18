package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

public class RemoveById extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id id", "Remove element by id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (arguments.length != 2 | arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");
        try {
            long id = Long.parseLong(arguments[1]);
            if (collectionManager.getById(id) == null) return new Response(false, "Element with id: " + id + " hasn't been found");
            collectionManager.removeById(id);
            return new Response("Element (id: " + id + ") has been removed successfully");
        } catch (NumberFormatException exception) {
            return new Response(false, "ID should be an integer");
        }
    }
}
