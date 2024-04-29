package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;
import main.java.models.Movie;
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
    private CollectionManager collectionManager;

    public Update(Console console, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        super("update id {element}", "Update element by id", needCommandManager, needCollectionManager, isServerMethod);
        this.console = console;
    }

    @Override
    public Response apply() {
        Movie oldMovie = collectionManager.getById(id);
        if (oldMovie == null) return new Response(false, "There is no element with that id");
        if (collectionManager.containPassportId(movie.getOperator().getPassportID())) {
            collectionManager.makeDecId();
            return new Response(false, "Element with that passport id already exist");
        }
        if (collectionManager.update(movie)) return new Response("Movie has been changed successfully");
        return new Response(false, "Old movie hasn't been found");
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setCommandManager(CommandManager commandManager) {

    }
}
