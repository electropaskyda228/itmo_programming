package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.MpaaRating;
import main.java.utility.Console;
import main.java.utility.Response;

public class CountMpaa extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public CountMpaa (Console console, CollectionManager collectionManager) {
        super("count_greater_than_mpaa_rating mpaarating", "Count elements witch mpaa rating is bigger than one's");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (arguments.length != 2) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        try {
            MpaaRating mpaaRating = MpaaRating.getFromString(arguments[1]);
            return new Response(collectionManager.countElementsBiggerMpaaRating(mpaaRating) + "");

        } catch (IllegalStateException exception) {
            return new Response(false, "You have chosen the wrong option. Choose from (G|PG|PG 13|R|NC 17)");
        } catch (CloneNotSupportedException exception) {
            return new Response(false, "Error with cloning");
        }
    }
}
