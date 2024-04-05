package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.models.Movie;
import main.java.models.Question;
import main.java.utility.Console;
import main.java.utility.Response;

public class RemoveGreater extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater {element}", "Remove all elements greater than specified");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

            Movie movie = Question.questionToMovie(console, collectionManager.getFreeId());
            collectionManager.makeDecId();

            if (movie != null && movie.validate()) {
                if (collectionManager.removeGreater(movie)) return new Response("All elements greater than specified hasn't been removed");
                return new Response("There is no element greater than specified");
            } else return new Response(false, "Movie's fields are not valid. Movie hasn't been created");
        } catch (Question.QuestionBreak exception) {
            return new Response(false, "Cancel");
        } catch (CloneNotSupportedException exception) {
            return new Response(false, "Error with cloning");
        }
    }
}
