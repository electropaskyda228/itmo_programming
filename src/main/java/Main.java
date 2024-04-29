package main.java;

import main.java.commands.*;
import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.managers.ConnectionManager;
import main.java.managers.DumpManager;
import main.java.utility.StandartConsole;

public class Main {
    public static void main(String[] args) {
        var console = new StandartConsole();

        if (args.length == 0) {
            console.println(
                    "Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        var dumpManager = new DumpManager(args[0], console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.init()) {
            System.exit(1);
        }
        var commandManager = new CommandManager() {{
            register("add", new Add(console, false, true, true));
            register("clear", new Clear(console, false, true, true));
            register("add_if_min", new AddIfMin(console, false, true, true));
            register("count_greater_than_mpaa_rating", new CountMpaa(console, false, true, true));
            register("filter_starts_with_name", new FilterWithName(console, false, true, true));
            register("help", new Help(console, true, false, true));
            register("info", new Info(console, false, true, true));
            register("print_field_ascending_mpaa_rating", new PrintElementAsc(console, false, true, true));
            register("remove_by_id id", new RemoveById(console, false, true, true));
            register("remove_greater", new RemoveGreater(console, false, true, true));
            register("show", new Show(console, false, true, true));
            register("update", new Update(console, false, true, true));
        }};

        ConnectionManager connectionManager = new ConnectionManager(collectionManager, commandManager);
        connectionManager.start();

        while (true) {
            if (console.hasNextLine()){
                String inputData = console.readln();
                if (inputData.equals("save")) {
                    connectionManager.interrupt();
                }
                if (inputData.equals("exit")) {
                    connectionManager.interrupt();
                    break;
                }
                console.print(console.getPrompt());
            }
        }

        console.print("Collection saved. Process ended");

    }
}