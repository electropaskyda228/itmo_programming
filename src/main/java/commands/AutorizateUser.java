package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.DumpManager;
import main.java.models.User;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class AutorizateUser extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Console console;
    private User userTemporary;

    public AutorizateUser(Console console) {
        super("autorizate_user", "Autorizate user");
        this.console = console;
    }

    @Override
    public Response apply() {
        int user_id = DumpManager.getInstance().autorizateUser(userTemporary);
        if (user_id != 0) {
            return new Response(true, "User has been autorizated successfully");
        }
        return new Response(false, "User hasn't been autorizated successfully");
    }
}
