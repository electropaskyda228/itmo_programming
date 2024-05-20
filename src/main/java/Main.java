package main.java;

import main.java.commands.*;
import main.java.managers.*;
import main.java.utility.StandartConsole;

public class Main {
    public static void main(String[] args) {
        var console = new StandartConsole();

        DumpManager dumpManager = DumpManager.getInstance();
        dumpManager.setConsole(console);
        dumpManager.setUrl("jdbc:postgresql://localhost:5432/movie_collection");
        if (!dumpManager.setDriver("org.postgresql.Driver")) {
            System.exit(1);
        }
        CollectionManager.getInstance().init();
        CommandManager commandManager = CommandManager.getInstance();
        commandManager.register("add", new Add(console));
        commandManager.register("clear", new Clear(console));
        commandManager.register("add_if_min", new AddIfMin(console));
        commandManager.register("count_greater_than_mpaa_rating", new CountMpaa(console));
        commandManager.register("filter_starts_with_name", new FilterWithName(console));
        commandManager.register("help", new Help(console));
        commandManager.register("info", new Info(console));
        commandManager.register("print_field_ascending_mpaa_rating", new PrintElementAsc(console));
        commandManager.register("remove_by_id id", new RemoveById(console));
        commandManager.register("remove_greater", new RemoveGreater(console));
        commandManager.register("show", new Show(console));
        commandManager.register("update", new Update(console));
        commandManager.register("register_user", new RegisterUser(console));
        commandManager.register("autorizate_user", new AutorizateUser(console));

       ConnectionManager connectionManager = ConnectionManager.getInstance();
       connectionManager.start();

        while (true) {
            if (console.hasNextLine()){
                String inputData = console.readln();
                if (inputData.equals("save")) {
                    connectionManager.interrupt();
                }
                if (inputData.equals("exit")) {
                    connectionManager.setStop(true);
                    connectionManager.interrupt();
                    break;
                }
                console.print(console.getPrompt());
            }
        }

        console.println("Collection saved. Process ended");
    }
}