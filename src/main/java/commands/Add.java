package main.java.commands;

import main.java.models.Movie;
import main.java.models.Question;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Add extends Command implements Serializable {
    private final Console console;
    private Movie movie;
    @Serial
    private static final long serialVersionUID = 1L;

    public Add(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("add", "Add new element", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response createArguments(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

            movie = Question.questionToMovie(console, -1);

            if (movie == null || !movie.validate()) {
                return new Response(false,
                        "Movie's fields are not valid. Movie hasn't been created");
            }

            return new Response(true, "Arguments has added successfully");

        } catch (Question.QuestionBreak exception) {
            return new Response(false, "Cancel");
        }
    }
}
