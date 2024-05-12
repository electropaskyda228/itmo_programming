package main.java.managers;

import main.java.commands.Command;
import main.java.utility.Response;


public class RequestManager {

    private RequestManager() {}

    private static class RequestManagerHolder {
        private static final RequestManager HOLDER_INSTANCE = new RequestManager();
    }

    public static RequestManager getInstance() {
        return RequestManagerHolder.HOLDER_INSTANCE;
    }
    public Response getRequest(Command receivedObject) {
        CommandManager.getInstance().addToHistory(receivedObject.getName());
        return receivedObject.apply();
    }
}
