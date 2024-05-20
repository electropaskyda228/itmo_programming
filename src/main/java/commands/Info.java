package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Info extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;

    public Info(Console console) {
        super("info", "Show info");
        this.console = console;
    }

    @Override
    public Response apply() {
        CollectionManager collectionManager = CollectionManager.getInstance();
        return new Response(collectionManager.getInfo());
    }
}
