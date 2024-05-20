package main.java.managers;

import main.java.commands.Changable;
import main.java.commands.Command;
import main.java.utility.Response;

public class RequestManagerProxy implements CanGetRequest {
    private final CanGetRequest requestGetter = RequestManager.getInstance();

    private RequestManagerProxy() {}

    private static class RequestManagerProxyHolder {
        private static final RequestManagerProxy HOLDER_INSTANCE = new RequestManagerProxy();
    }

    public static RequestManagerProxy getInstance() {
        return RequestManagerProxyHolder.HOLDER_INSTANCE;
    }

    @Override
    public Response getRequest(Command command) {
        try {
            Changable changeCommand = (Changable) command;
            if (DumpManager.getInstance().checkRightsForChange(command.getUser(), changeCommand.getId()) == 0)
                return new Response(false, "Declined. You have no rights for changing this object");
        } catch (ClassCastException ignored) {}
        return requestGetter.getRequest(command);
    }

    public void stopMachine() {
        requestGetter.stopMachine();
    }
}
