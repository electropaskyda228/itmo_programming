package main.java;

import main.java.commands.*;
import main.java.managers.*;
import main.java.utility.StandartConsole;

public class Main {
    public static void main(String[] args) {
        var console = new StandartConsole();

        if (args.length == 0) {
            console.println(
                    "Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        DumpManager.getInstance().setConsole(console);
        DumpManager.getInstance().setFileName(args[0]);
        if (!CollectionManager.getInstance().init()) {
            System.exit(1);
        }
        CommandManager.getInstance().register("add", new Add(console));
        CommandManager.getInstance().register("clear", new Clear(console));
        CommandManager.getInstance().register("add_if_min", new AddIfMin(console));
        CommandManager.getInstance().register("count_greater_than_mpaa_rating", new CountMpaa(console));
        CommandManager.getInstance().register("filter_starts_with_name", new FilterWithName(console));
        CommandManager.getInstance().register("help", new Help(console));
        CommandManager.getInstance().register("info", new Info(console));
        CommandManager.getInstance().register("print_field_ascending_mpaa_rating", new PrintElementAsc(console));
        CommandManager.getInstance().register("remove_by_id id", new RemoveById(console));
        CommandManager.getInstance().register("remove_greater", new RemoveGreater(console));
        CommandManager.getInstance().register("show", new Show(console));
        CommandManager.getInstance().register("update", new Update(console));

       ConnectionManager.getInstance().start();

        while (true) {
            if (console.hasNextLine()){
                String inputData = console.readln();
                if (inputData.equals("save")) {
                    ConnectionManager.getInstance().interrupt();
                }
                if (inputData.equals("exit")) {
                    ConnectionManager.getInstance().setStop(true);
                    ConnectionManager.getInstance().interrupt();
                    break;
                }
                console.print(console.getPrompt());
            }
        }

        console.print("Collection saved. Process ended");
    }
}