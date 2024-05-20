package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

public class PrintElementAsc extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;

    public PrintElementAsc(Console console) {
        super("print_field_ascending_mpaa_rating", "Show ascending fields mpaa rating of all elements");
        this.console = console;
    }

    @Override
    public Response apply() {
        CollectionManager collectionManager = CollectionManager.getInstance();
        return new Response(collectionManager.getAllMpaarating());
    }
}
