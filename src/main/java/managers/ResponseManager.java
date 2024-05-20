package main.java.managers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ResponseManager {
    private final ExecutorService service;

    private ResponseManager() {
        this.service = Executors.newCachedThreadPool();
    }

    private static class ResponseManagerHolder {
        private static final ResponseManager HOLDER_INSTANCE = new ResponseManager();
    }

    public static ResponseManager getInstance() {
        return ResponseManagerHolder.HOLDER_INSTANCE;
    }


    public Future<ByteBuffer> makeResponse(Serializable object) throws InterruptedException {
        ArrayList<Callable<ByteBuffer>> temporaryCollection = new ArrayList<>(1);
        temporaryCollection.add(new MakeResponseTask(object));
        return service.invokeAll(temporaryCollection).get(0);
    }

    private class MakeResponseTask implements Callable<ByteBuffer> {
        private final Serializable object;
        public MakeResponseTask(Serializable object) {
            this.object = object;
        }

        @Override
        public ByteBuffer call() {
            try {
                return toBuffer(object);
            } catch (IOException exception) {
                return null;
            }

        }
        /*
        Converting object into ByteBuffer
        @param object - serializable object
        @return return object converted in ByteBuffer
        @throws IOException input output exception
         */
        private ByteBuffer toBuffer(Serializable object) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            byte[] data = baos.toByteArray();
            int length = data.length + 4;
            ByteBuffer writeBuffer = ByteBuffer.allocate(length);
            writeBuffer.putInt(data.length);
            writeBuffer.put(data);
            return writeBuffer;
        }
    }

    public void stopMachine() {
        service.close();
    }


}
