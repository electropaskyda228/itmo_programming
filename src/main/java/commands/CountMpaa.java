package main.java.commands;

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
        setIsServerMethod(true);
    }

    @Override
    public Response createArguments(String[] arguments) {
        if (arguments.length != 2) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        try {
            this.mpaaRating = MpaaRating.getFromString(arguments[1]);
            if (this.mpaaRating == null) throw new IllegalStateException();
            return new Response(true, "Arguments has added successfully");

        } catch (IllegalStateException exception) {
            return new Response(false, "You have chosen the wrong option. Choose from (G|PG|PG 13|R|NC 17)");
        }
    }
}
