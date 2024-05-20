package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class FilterWithName extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private String movieName;

    public FilterWithName(Console console) {
        super("filter_starts_with_name name", "Show elements which names start with specified");
        this.console = console;
    }

    @Override
    public Response apply() {
        CollectionManager collectionManager = CollectionManager.getInstance();
        return new Response(collectionManager.filterStartsWithName(this.movieName));
    }

}
