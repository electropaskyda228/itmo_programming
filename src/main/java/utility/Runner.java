package main.java.utility;

import main.java.managers.CommandManager;
import java.util.*;

public class Runner {
    private final Console console;
    private final CommandManager commandManager;
    private final LauncherCommand launcherCommand;


    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
        this.launcherCommand = new LauncherCommand(commandManager);
    }


    public void interactiveMode() {
        try {
            String[] userCommand = {"", ""};

            console.prompt();
            while (console.hasNext()) {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandManager.addToHistory(userCommand[0]);
                Response serverResponse = launchCommand(userCommand);

                String result = serverResponse.getMessage();
                if (!(result.endsWith("exit"))){
                    console.println(result);
                }
                if (result.endsWith("exit")) break;
                console.prompt();
            }
        } catch (NoSuchElementException exception) {
            console.printError("User's main.java.commands haven't been detected");
        } catch (IllegalStateException exception) {
            console.printError("I don't know what it is");
        }
    }


    private Response launchCommand(String[] userCommand) {
        return launcherCommand.loadCommand(userCommand);
    }
}
