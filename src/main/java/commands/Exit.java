package main.java.commands;

import main.java.utility.Console;
import main.java.utility.Response;

public class Exit extends Command {
    private final Console console;

    public Exit(Console console) {
        super("exit", "Exit programm");
        this.console = console;
    }

    @Override
    public Response apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        return new Response("exit");
    }
}
