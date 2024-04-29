package main.java.commands;

import main.java.managers.CollectionManager;
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
    private CommandManager commandManager;

    public Help(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("help", "Show help information", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        return new Response(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {

    }

    @Override
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
