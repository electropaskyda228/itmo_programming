package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.DumpManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Clear extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;

    public Clear(Console console) {
        super("clear", "Clear collection");
        this.console = console;
    }

    @Override
    public Response apply() {
        if (DumpManager.getInstance().clear(getUser())) {
            CollectionManager collectionManager = CollectionManager.getInstance();
            collectionManager.init();
            return new Response("Collection are cleared");
        }
        return new Response("No element was deleted");
    }

}
