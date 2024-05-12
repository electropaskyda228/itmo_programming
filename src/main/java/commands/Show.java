package main.java.commands;

import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Show extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;

    public Show(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("show", "Show all elements", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response createArguments(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        return new Response(true, "Arguments has added successfully");
    }
}
