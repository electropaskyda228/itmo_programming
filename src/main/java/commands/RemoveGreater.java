package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.DumpManager;
import main.java.models.Movie;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class RemoveGreater extends Command implements Serializable {

    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private Movie movie;

    public RemoveGreater(Console console) {
        super("remove_greater {element}", "Remove all elements greater than specified");
        this.console = console;
    }

    @Override
    public Response apply() {
        try {
            if (DumpManager.getInstance().removeGreater(getUser(), movie)) {
                CollectionManager collectionManager = CollectionManager.getInstance();
                collectionManager.init();
                return new Response("All elements greater than specified hasn't been removed");
            }
            return new Response("There is no element greater than specified");
        } catch (Exception exception) {
            return new Response(false, "Unexceptionable exception");
        }
    }
}
