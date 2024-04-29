package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Info extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private CollectionManager collectionManager;

    public Info(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("info", "Show info", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        return new Response(collectionManager.getInfo());
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setCommandManager(CommandManager commandManager) {

    }
}
