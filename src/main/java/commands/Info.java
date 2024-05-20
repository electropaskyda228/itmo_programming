package main.java.commands;

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
        setIsServerMethod(true);
    }

    @Override
    public Response createArguments(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        return new Response(true, "Arguments has added successfully");
    }
}
