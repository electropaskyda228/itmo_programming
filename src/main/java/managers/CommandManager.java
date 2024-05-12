package main.java.managers;

import main.java.commands.Command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final List<String> commandHistory = new ArrayList<>();

    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

    public void addToHistory(String command) {
        commandHistory.add(command);
    }

    private CommandManager(){}

    private static class CommandManagerHolder {
        private static final CommandManager HOLDER_INSTANCE = new CommandManager();
    }

    public static CommandManager getInstance() {
        return CommandManagerHolder.HOLDER_INSTANCE;
    }

}
