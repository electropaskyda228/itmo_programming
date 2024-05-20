package main.java.commands;

import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class History extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private final CommandManager commandManager;

    public History(Console console, CommandManager commandManager) {
        super("history", "Show previous five main.java.commands");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public Response createArguments(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        return new Response(String.join("\n", commandManager.getCommandHistory()));
    }
}
