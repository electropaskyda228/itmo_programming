package main.java.utility;

import main.java.commands.Command;
import main.java.managers.CommandManager;
import main.java.models.CurrentUser;
import main.java.models.Question;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LauncherCommand implements CommandLaunchable {
    private final CommandManager commandManager;
    private Stack<String> scripts = new Stack<>();
    public LauncherCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    private Command findCommand(String[] userCommand) {
        return commandManager.getCommand(userCommand[0]);
    }
    @Override
    public Response loadCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new Response("");
        if (userCommand[0].equals("execute_script")) {
            if (userCommand[1].isEmpty()) return new Response(false, "No file name");
            return scriptMode(userCommand[1]);
        }

        Command command = findCommand(userCommand);
        if (command == null) return new Response(false,
                "Command '" + userCommand[0] + "' hasn't been found.");
        command.setUser(CurrentUser.getInstance().getCurrentUser());
        Response responseCreateArguments = command.createArguments(userCommand);
        if (responseCreateArguments.getMessage().equals("Cancel")) return responseCreateArguments;
        if (!responseCreateArguments.getExitCode()) return responseCreateArguments;

        if (command.orderToServer()) return sendCommandToServer(command);
        return responseCreateArguments;
    }

    private Response sendCommandToServer(Command command) {
        try{
            TCPClient client = new TCPClient("localhost", 6789);
            Response response =  client.sendObject(command);
            client.closeChannel();
            return response;
        } catch (IOException exception) {
            return new Response(false, "Connection lost");
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
                    System.out.println(1);
                    commandStatus = loadCommand(userCommand);
                    i += j - i - 1 - Question.getFileInformationSize();
                    Question.setDefaultFileInformation();
                } else commandStatus = loadCommand(userCommand);
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
}
