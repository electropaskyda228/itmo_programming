package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class RemoveById extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;

    public RemoveById(Console console) {
        super("remove_by_id id", "Remove element by id");
        this.console = console;
    }

    @Override
    public Response apply() {
        CollectionManager collectionManager = CollectionManager.getInstance();
        if (collectionManager.getById(id) == null) return new Response(false, "Element with id: " + id + " hasn't been found");
        collectionManager.removeById(id);
        return new Response("Element (id: " + id + ") has been removed successfully");
    }
}
