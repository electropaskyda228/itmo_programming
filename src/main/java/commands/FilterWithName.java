package main.java.commands;

import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class FilterWithName extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private String movieName;

    public FilterWithName(Console console) {
        super("filter_starts_with_name name", "Show elements which names start with specified");
        this.console = console;
        setIsServerMethod(true);
    }

    @Override
    public Response createArguments(String[] arguments) {
        if (arguments.length != 2) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");
        this.movieName = arguments[1];

        return new Response(true, "Arguments has added successfully");
    }
}
