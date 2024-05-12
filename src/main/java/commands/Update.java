package main.java.commands;

import main.java.models.Movie;
import main.java.models.Question;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Update extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private Movie movie;
    private long id;

    public Update(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("update id {element}", "Update element by id", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response createArguments(String[] arguments) {
        if (arguments.length != 2 | arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        try {
            this.id = Long.parseLong(arguments[1]);
            this.movie = Question.questionToMovie(console, id);

            if (movie == null || !movie.validate()) return new Response(false,
                    "Movie's fields are not valid. Movie hasn't been changed");
            return new Response(true, "Arguments has added successfully");
        } catch (Question.QuestionBreak exception) {
            return new Response(false, "Cancel");
        } catch (NumberFormatException exception) {
            return new Response(false, "ID should be an integer");
        }
    }
}
