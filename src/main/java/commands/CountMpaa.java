package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.models.MpaaRating;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class CountMpaa extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private CollectionManager collectionManager;
    private MpaaRating mpaaRating;

    public CountMpaa(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("count_greater_than_mpaa_rating mpaarating", "Count elements witch mpaa rating is bigger than one's",
                needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        try {
            return new Response(collectionManager.countElementsBiggerMpaaRating(mpaaRating) + "");
        } catch (CloneNotSupportedException exception) {
            return new Response(false, "Error with cloning");
        }
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setCommandManager(CommandManager commandManager) {

    }
}
