package main.java.commands;

import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;
import java.util.stream.Collectors;

public class Help extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("help", "Show help information", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
        this.commandManager =  commandManager;
    }

    @Override
    public Response createArguments(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        return new Response(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }
}
