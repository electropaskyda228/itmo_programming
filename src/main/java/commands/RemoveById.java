package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.DumpManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class RemoveById extends Command implements Serializable, Changable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;

    public RemoveById(Console console) {
        super("remove_by_id id", "Remove element by id");
        this.console = console;
    }

    @Override
    public Response apply() {
        short may = DumpManager.getInstance().removeElementById(id);
        if (may == -1) return new Response(false, "Deletion error");
        else if (may == 0) return new Response(false, "Element with id:" + id + " hasn't been found");
        CollectionManager collectionManager = CollectionManager.getInstance();
        collectionManager.removeById(id);
        return new Response("Element (id: " + id + ") has been removed successfully");
    }

    @Override
    public long getId() {
        return id;
    }
}
