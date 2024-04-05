package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.Movie;
import main.java.models.Question;
import main.java.utility.Console;
import main.java.utility.Response;

public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add", "Add new element");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

            Movie movie = Question.questionToMovie(console, collectionManager.getFreeId());

            if (movie != null && movie.validate()) {
                if (collectionManager.containPassportId(movie.getOperator().getPassportID())) {
                    collectionManager.makeDecId();
                    return new Response(false, "Element with that passport id already exist");
                }
                collectionManager.addElement(movie);
                return new Response("Movie has been added successfully");
            }
            collectionManager.makeDecId();
            return new Response(false, "Movie's fields are not valid. Movie hasn't been created");
        } catch (Question.QuestionBreak exception) {
            return new Response(false, "Cancel");
        }
    }
}
