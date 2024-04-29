package main.java.managers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadManager {
    Serializable read(SocketChannel client) throws IOException, ClassNotFoundException {
        ByteBuffer sizeBuffer = ByteBuffer.allocate(4); // Integer.BYTES == 4
        client.read(sizeBuffer);
        sizeBuffer.flip();
        int size = sizeBuffer.getInt();

        ByteBuffer dataBuffer = ByteBuffer.allocate(size);
        client.read(dataBuffer);
        dataBuffer.flip();
        return fromByteBuffer(dataBuffer);
    }
    /*
    Converting object into ByteBuffer
    @param byteBuffer
    @return return deserialized object
    @throws IOException, ClassNotFoundException
     */
    public static Serializable fromByteBuffer(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(bais);

        Serializable response = (Serializable) objectInputStream.readObject();

        objectInputStream.close();
        bais.close();

        return response;
    }
}
