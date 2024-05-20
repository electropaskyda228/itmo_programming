package main.java.commands;

import main.java.models.Movie;
import main.java.models.Question;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class RemoveGreater extends Command implements Serializable {

    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private Movie movie;

    public RemoveGreater(Console console) {
        super("remove_greater {element}", "Remove all elements greater than specified");
        this.console = console;
        setIsServerMethod(true);
    }

    @Override
    public Response createArguments(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

            this.movie = Question.questionToMovie(console, -1);
            if (movie != null && movie.validate()) {
                return new Response(true, "Arguments has added successfully");
            } else return new Response(false, "Movie's fields are not valid. Movie hasn't been created");
        } catch (Question.QuestionBreak exception) {
            return new Response(false, "Cancel");
        }
    }
}
