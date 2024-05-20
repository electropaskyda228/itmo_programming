package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.DumpManager;
import main.java.models.User;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class RegisterUser extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private User userTemporary;

    public RegisterUser(Console console) {
        super("register_user", "Register new user");
        this.console = console;
    }

    @Override
    public Response apply() {
        int id = DumpManager.getInstance().registerUser(userTemporary);
        switch (id) {
            case -1 -> {
                return new Response(false, "User hasn't been added");
            }
            case 0 -> {
                return new Response(false, "User with this name already exists");
            }
            default -> {
                CollectionManager.getInstance().addUser(getUser(), id);
                return new Response("User has been added");
            }
        }
    }
}
