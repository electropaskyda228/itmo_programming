package main.java.managers;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ReadManager {

    private ReadManager() {}
    private static class ReadManagerHolder {
        private static final ReadManager HOLDER_INSTANCE = new ReadManager();
    }
    public static ReadManager getInstance() {
        return ReadManagerHolder.HOLDER_INSTANCE;
    }

    public Future<Serializable> read(SocketChannel client) {
        FutureTask<Serializable> readResult = new FutureTask<>(new ReadTask(client));
        new Thread(readResult).start();
        return readResult;
    }

    private class ReadTask implements Callable<Serializable> {
        private final SocketChannel channel;

        public ReadTask(SocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public Serializable call() {
            try {
                ByteBuffer sizeBuffer = ByteBuffer.allocate(4); // Integer.BYTES == 4
                channel.read(sizeBuffer);
                sizeBuffer.flip();
                int size = sizeBuffer.getInt();

                ByteBuffer dataBuffer = ByteBuffer.allocate(size);
                channel.read(dataBuffer);
                dataBuffer.flip();
                return fromByteBuffer(dataBuffer);
            } catch (IOException | ClassNotFoundException exception) {
                return null;
            }
        }
        /*
        Converting object into ByteBuffer
        @param byteBuffer
        @return return deserialized object
        @throws IOException, ClassNotFoundException
         */
        private Serializable fromByteBuffer(ByteBuffer buffer) throws IOException, ClassNotFoundException {
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);

            Serializable response = (Serializable) objectInputStream.readObject();

            objectInputStream.close();
            bais.close();

            return response;
        }
    }

}
