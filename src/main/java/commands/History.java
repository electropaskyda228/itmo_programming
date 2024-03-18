package main.java.commands;

import main.java.managers.CommandManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.util.List;

public class History extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public History(Console console, CommandManager commandManager) {
        super("history", "Show previous five main.java.commands");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        List<String> history = commandManager.getCommandHistory();
        int count = 1;
        String answer = "";
        while (history.size() - count >= 0) {
            answer += history.get(history.size() - count) + "\n";
            count += 1;
        }
        return new Response(answer);
    }
}
