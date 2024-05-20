package main.java.managers;

import main.java.commands.Command;
import main.java.utility.Response;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ThreadExecuteManager {
    private final ExecutorService service;
    private ThreadExecuteManager() {
        this.service = Executors.newFixedThreadPool(100);
    }
    private static class ThreadExecuteManagerHolder {
        private static final ThreadExecuteManager HOLDER_INSTANCE = new ThreadExecuteManager();
    }
    public static ThreadExecuteManager getInstance() {
        return ThreadExecuteManagerHolder.HOLDER_INSTANCE;
    }

    public Future<ByteBuffer> makeActions(SocketChannel socketChannel) throws InterruptedException {
        ArrayList<Callable<ByteBuffer>> temporaryCollection = new ArrayList<>(1);
        temporaryCollection.add(new Task(socketChannel));
        return service.invokeAll(temporaryCollection).get(0);
    }

    private class Task implements Callable<ByteBuffer> {
        private final SocketChannel client;
        public Task(SocketChannel client) {
            this.client = client;
        }

        @Override
        public ByteBuffer call() throws InterruptedException, ExecutionException {
            Future<Serializable> readResult = ReadManager.getInstance().read(client);
            Command command = (Command) readResult.get();
            if (command == null) return null;
            CanGetRequest requestManager = RequestManagerProxy.getInstance();
            Response resultResponse = requestManager.getRequest(command);
            return ResponseManager.getInstance().makeResponse(resultResponse).get();
        }
    }
    public void stopMachine() {
        service.close();
    }
}
