package main.java.managers;

import main.java.commands.Command;
import main.java.utility.Response;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class RequestManager implements CanGetRequest {
    private final ForkJoinPool service;

    private RequestManager() {
        service = ForkJoinPool.commonPool();
    }

    private static class RequestManagerHolder {
        private static final RequestManager HOLDER_INSTANCE = new RequestManager();
    }

    public static RequestManager getInstance() {
        return RequestManagerHolder.HOLDER_INSTANCE;
    }
    public Response getRequest(Command receivedObject) {
        CommandManager.getInstance().addToHistory(receivedObject.getName());
        return service.invoke(new RequestRecursiveTask(receivedObject));
    }

    private class RequestRecursiveTask extends RecursiveTask<Response> {
        private final Command command;

        public RequestRecursiveTask(Command command) {
            this.command = command;
        }
        @Override
        protected Response compute() {
            return command.apply();
        }
    }

    public void stopMachine() {
        service.close();
    }
}
