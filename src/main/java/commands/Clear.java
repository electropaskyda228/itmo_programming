package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Clear extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private CollectionManager collectionManager;

    public Clear(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("clear", "Clear collection", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        collectionManager.clear();
        return new Response("Collection are cleared");
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setCommandManager(CommandManager commandManager) {

    }
}
