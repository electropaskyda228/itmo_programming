package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class Show extends Command implements Serializable {
    private final Console console;
    @Serial
    private static final long serialVersionUID = 1L;

    public Show(Console console) {
        super("show", "Show all elements");
        this.console = console;
    }

    @Override
    public Response apply() {
        try{
            return new Response(CollectionManager.getInstance().toString());
        } catch (Exception e) {
            return new Response(false, "Collection can't be shown");
        }
    }
}
