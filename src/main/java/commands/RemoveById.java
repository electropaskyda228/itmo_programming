package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class RemoveById extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private CollectionManager collectionManager;

    public RemoveById(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("remove_by_id id", "Remove element by id", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        if (collectionManager.getById(id) == null) return new Response(false, "Element with id: " + id + " hasn't been found");
        collectionManager.removeById(id);
        return new Response("Element (id: " + id + ") has been removed successfully");
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setCommandManager(CommandManager commandManager) {

    }
}
