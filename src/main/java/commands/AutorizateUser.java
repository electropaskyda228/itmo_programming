package main.java.commands;

import main.java.models.CurrentUser;
import main.java.models.Question;
import main.java.models.User;
import main.java.utility.Console;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class AutorizateUser extends Command implements Serializable {
    private final Console console;
    private User userTemporary;
    @Serial
    private static final long serialVersionUID = 1L;
    public AutorizateUser(Console console) {
        super("autorizate_user", "Autorize user");
        this.console = console;
        setIsServerMethod(true);
    }

    @Override
    public Response createArguments(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new Response(false, "Wrong number of arguments\nUsing: '" + getName() + "'");

            userTemporary = Question.questionToUser(console);

            if (userTemporary == null || !userTemporary.validate()) {
                return new Response(false,
                        "User's fields are not valid. User hasn't been created");
            }

            CurrentUser.getInstance().setCurrentUser(userTemporary);

            return new Response(true, "Arguments has added successfully");

        } catch (Question.QuestionBreak exception) {
            return new Response(false, "Cancel");
        }
    }
}

