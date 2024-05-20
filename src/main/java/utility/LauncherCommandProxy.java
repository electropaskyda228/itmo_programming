package main.java.utility;

import main.java.managers.CommandManager;
import main.java.models.CurrentUser;

import java.util.Arrays;

public class LauncherCommandProxy implements CommandLaunchable {
    private final CommandLaunchable launcher;
    private final String[] allowedCommands = new String[]{"autorizate_user", "exit", "help", "history", "register_user"};
    public LauncherCommandProxy(CommandManager commandManager) {
        launcher = new LauncherCommand(commandManager);
    }
    @Override
    public Response loadCommand(String[] userCommand) {
        boolean may = Arrays.stream(allowedCommands).anyMatch((x) -> userCommand[0].equals(x));
        if (CurrentUser.getInstance().getCurrentUser() == null && !may) return new Response(false, "Declined. You are not autorized");
        return launcher.loadCommand(userCommand);
    }
}
