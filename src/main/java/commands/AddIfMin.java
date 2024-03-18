package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.Movie;
import main.java.models.Question;
import main.java.utility.Console;
import main.java.utility.Response;

public class AddIfMin extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddIfMin(Console console, CollectionManager collectionManager) {
        super("add_if_min", "Add element if its value less than the least element of collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

            Movie movie = Question.questionToMovie(console, collectionManager.getFreeId());

            if (movie == null || !movie.validate()) {
                collectionManager.makeDecId();
                return new Response(false,
                        "Movie's fields are not valid. Movie hasn't been created");
            }
            if (collectionManager.containPassportId(movie.getOperator().getPassportID())) {
                collectionManager.makeDecId();
                return new Response(false, "Element with that passport id already exist");
            }
            if (collectionManager.checkElementLessMinimum(movie)) {
                collectionManager.addElement(movie);
                return new Response("Movie has been added successfully");
            }
            collectionManager.makeDecId();
            return new Response(false, "Element is bigger than the least of collection. Element hasn't been added.");
        } catch (Question.QuestionBreak exception) {
            return new Response(false, "Cancel");
        }
    }
}
