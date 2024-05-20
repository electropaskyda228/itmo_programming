package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.DumpManager;
import main.java.models.Movie;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Update extends Command implements Serializable, Changable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private Movie movie;
    private long id;

    public Update(Console console) {
        super("update id {element}", "Update element by id");
        this.console = console;
    }

    @Override
    public Response apply() {
        DumpManager dumpManager = DumpManager.getInstance();
        CollectionManager collectionManager = CollectionManager.getInstance();
        if (collectionManager.containPassportId(movie.getOperator().getPassportID())) {
            return new Response(false, "Element with that passport id already exist");
        }
        short checkResult = dumpManager.checkRightsForChange(getUser(), movie.getId());
        if (checkResult == -1) {
            return new Response(false, "There is no element with such id");
        } else if (checkResult == 0) {
            return new Response(false, "You have no rights for changing this element");
        }
        if (!dumpManager.updateElement(movie)) return new Response(false, "Old movie hasn't been found");
        if (collectionManager.update(movie)) return new Response("Movie has been changed successfully");
        return new Response(false, "Old movie hasn't been found");
    }

    @Override
    public long getId() {
        return id;
    }
}
