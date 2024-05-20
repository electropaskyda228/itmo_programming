package main.java.managers;

import main.java.commands.Command;
import main.java.utility.Response;

public interface CanGetRequest {
    Response getRequest(Command command);
    void stopMachine();
}
