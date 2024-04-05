package main.java;

import main.java.commands.*;
import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.managers.DumpManager;
import main.java.utility.Runner;
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
            register("help", new Help(console, this));
            register("history", new History(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("exit", new Exit(console));
            register("add_if_min", new AddIfMin(console, collectionManager));
            register("count_greater_than_mpaa_rating", new CountMpaa(console, collectionManager));
            register("filter_starts_with_name", new FilterWithName(console, collectionManager));
            register("print_field_ascending_mpaa_rating", new PrintElementAsc(console, collectionManager));
            register("remove_greater", new RemoveGreater(console, collectionManager));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
}