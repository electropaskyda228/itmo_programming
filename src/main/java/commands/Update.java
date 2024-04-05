package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.Movie;
import main.java.models.Question;
import main.java.utility.Console;
import main.java.utility.Response;

public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager) {
        super("update id {element}", "Update element by id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        if (arguments.length != 2 | arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

        try {
            long id = Long.parseLong(arguments[1]);
            Movie oldMovie = collectionManager.getById(id);
            if (oldMovie == null) return new Response(false, "There is no element with that id");
            Movie movie = Question.questionToMovie(console, id);

            if (movie == null || !movie.validate()) return new Response(false,
                    "Movie's fields are not valid. Movie hasn't been changed");
            if (collectionManager.containPassportId(movie.getOperator().getPassportID())) {
                collectionManager.makeDecId();
                return new Response(false, "Element with that passport id already exist");
            }
            if (collectionManager.update(movie)) return new Response("Movie has been changed successfully");
            return new Response(false, "Old movie hasn't been found");
        } catch (Question.QuestionBreak exception) {
            return new Response(false, "Cancel");
        } catch (NumberFormatException exception) {
            return new Response(false, "ID should be an integer");
        }
    }
}
