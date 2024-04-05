package main.java.utility;

import main.java.managers.CommandManager;
import main.java.models.Movie;
import main.java.models.Question;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Runner {
    private Console console;
    private final CommandManager commandManager;
    private final TreeSet<Movie> scriptStack = new TreeSet<>();
    private Stack<String> scripts = new Stack<>();


    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }


    public void interactiveMode() {
        try {
            Response commandStatus;
            String[] userCommand = {"", ""};

            console.prompt();
            while (console.hasNext()) {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandManager.addToHistory(userCommand[0]);
                commandStatus = launchCommand(userCommand);

                String result = commandStatus.getMessage();
                console.println((result.endsWith("exit")) ? result.substring(0, result.length()-4):result);
                if (result.endsWith("exit")) break;
                console.prompt();
            }
        } catch (NoSuchElementException exception) {
            console.printError("User's main.java.commands haven't been detected");
        } catch (IllegalStateException exception) {
            console.printError("I don't know what it is");
        }
    }

    private Response scriptMode(String filename) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            List<String> response = new ArrayList<>();
            var result = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                result.append(line).append("\n");
                line = bufferedReader.readLine();
            }

            if (scripts.search(filename) != -1) {
                response.add("Find circle");
                return new Response(String.join("\n", response));
            }
            scripts.add(filename);
            int i = 0;
            String[] commands = result.toString().split("\n");
            Response commandStatus;
            while (i < commands.length) {
                String[] userCommand = (commands[i].trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                String[] strangeCommand = {"update", "remove_greater", "add_if_min", "add"};
                if (Arrays.stream(strangeCommand).anyMatch(x -> x.equals(userCommand[0]))) {
                    List<String> information = new ArrayList<>();
                    int j = i + 1;
                    while (j<commands.length) {
                        information.add(commands[j]);
                        j++;
                    }
                    Question.setFileInfomation(information);
                    commandStatus = launchCommand(userCommand);
                    i += j - i - 1 - Question.getFileInformationSize();
                    Question.setDefaultFileInformation();
                } else commandStatus = launchCommand(userCommand);
                response.add(commandStatus.getMessage());
                if (commandStatus.getMessage().equals("exit")) return new Response(String.join("\n", response));
                i += 1;
            }
            return new Response(String.join("\n", response));


        } catch (FileNotFoundException exception) {
            return new Response(false, "File does not exist");
        } catch (IOException exception) {
            return new Response(false, "Reading file error");
        }
    }


    private Response launchCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new Response("");
        if (userCommand[0].equals("execute_script")) {
            if (userCommand[1].isEmpty()) return new Response(false, "No file name");
            return scriptMode(userCommand[1]);
        }
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) return new Response(false,
                "Command '" + userCommand[0] + "' hasn't been found.");
        else return command.apply(userCommand);
    }
}
