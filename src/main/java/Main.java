package main.java;

import main.java.commands.*;
import main.java.managers.CommandManager;
import main.java.utility.Runner;
import main.java.utility.StandartConsole;

public class Main {
    public static void main(String[] args) {
        var console = new StandartConsole();

        var commandManager = new CommandManager() {{
            register("add", new Add(console));
            register("clear", new Clear(console));
            register("exit", new Exit(console));
            register("add_if_min", new AddIfMin(console));
            register("count_greater_than_mpaa_rating", new CountMpaa(console));
            register("filter_starts_with_name", new FilterWithName(console));
            register("help", new Help(console, this));
            register("history", new History(console, this));
            register("info", new Info(console));
            register("print_field_ascending_mpaa_rating", new PrintElementAsc(console));
            register("remove_by_id", new RemoveById(console));
            register("remove_greater", new RemoveGreater(console));
            register("show", new Show(console));
            register("update", new Update(console));
            register("register_user", new RegisterUser(console));
            register("autorizate_user", new AutorizateUser(console));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
}