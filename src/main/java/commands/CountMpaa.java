package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.MpaaRating;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class CountMpaa extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private MpaaRating mpaaRating;

    public CountMpaa(Console console) {
        super("count_greater_than_mpaa_rating mpaarating", "Count elements witch mpaa rating is bigger than one's");
        this.console = console;
    }

    @Override
    public Response apply() {
        try {
            CollectionManager collectionManager = CollectionManager.getInstance();
            return new Response(true, collectionManager.countElementsBiggerMpaaRating(mpaaRating) + "");
        } catch (Exception exception) {
            return new Response(false, "Unexceptionable error");
        }
    }
}
