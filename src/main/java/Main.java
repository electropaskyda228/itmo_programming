package main.java;

import main.java.commands.*;
import main.java.managers.CommandManager;
import main.java.utility.Runner;
import main.java.utility.StandartConsole;

public class Main {
    public static void main(String[] args) {
        var console = new StandartConsole();

        var commandManager = new CommandManager() {{
            register("add", new Add(console, false, true, true));
            register("clear", new Clear(console, false, true, true));
            register("exit", new Exit(console, false, false, false));
            register("add_if_min", new AddIfMin(console, false, true, true));
            register("count_greater_than_mpaa_rating", new CountMpaa(console, false, true, true));
            register("filter_starts_with_name", new FilterWithName(console, false, true, true));
            register("help", new Help(console, this, true, false, false));
            register("history", new History(console, this, true, false, false));
            register("info", new Info(console, false, true, true));
            register("print_field_ascending_mpaa_rating", new PrintElementAsc(console, false, true, true));
            register("remove_by_id", new RemoveById(console, false, true, true));
            register("remove_greater", new RemoveGreater(console, false, true, true));
            register("show", new Show(console, false, true, true));
            register("update", new Update(console, false, true, true));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
}