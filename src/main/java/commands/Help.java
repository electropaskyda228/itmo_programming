package main.java.commands;

import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.util.stream.Collectors;

public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager) {
        super("help", "Show help information");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");


        return new Response(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }
}
