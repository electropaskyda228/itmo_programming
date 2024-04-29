package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class PrintElementAsc extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private CollectionManager collectionManager;

    public PrintElementAsc(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("print_field_ascending_mpaa_rating", "Show ascending fields mpaa rating of all elements",
                needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        return new Response(collectionManager.getAllMpaarating());
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setCommandManager(CommandManager commandManager) {

    }
}
