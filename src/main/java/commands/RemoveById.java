package main.java.commands;

import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class RemoveById extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;

    public RemoveById(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("remove_by_id id", "Remove element by id", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response createArguments(String[] arguments) {
        if (arguments.length != 2 | arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");
        try {
            this.id = Long.parseLong(arguments[1]);
            return new Response(true, "Arguments has added successfully");
        } catch (NumberFormatException exception) {
            return new Response(false, "ID should be an integer");
        }
    }
}
