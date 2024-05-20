package main.java.utility;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPClient implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private final String host;
    private final int port;
    private final SocketAddress address;
    private final SocketChannel client;

    public TCPClient(String host, int port) throws IOException {
        this.port = port;
        this.host = host;
        address = new InetSocketAddress(host, port);
        client = SocketChannel.open();
        client.connect(address);
        client.configureBlocking(false);
    }

    public void closeChannel() {
        try {
            client.close();
        } catch (IOException ignored) {}

    }

    /*
    Sending object to server and receiving response from it
    @param object - serialize object to send
    @return return (Response) response from server
     */
    public Response sendObject(Serializable object) {
        try {
            byte[] data = toBuffer(object);
            int length = data.length + 4;
            ByteBuffer writeBuffer = ByteBuffer.allocate(length);
            writeBuffer.putInt(data.length);
            writeBuffer.put(data);
            writeBuffer.flip();
            while (writeBuffer.hasRemaining()) {
                client.write(writeBuffer);
            }
        } catch (IOException exception) {
            return new Response(false, "Send information error");
        }

        try {

            ByteBuffer sizeBuffer = ByteBuffer.allocate(4); // Integer.BYTES == 4
            while (client.read(sizeBuffer) == 0) {}
            client.read(sizeBuffer);
            sizeBuffer.flip();
            int size = sizeBuffer.getInt();

            ByteBuffer dataBuffer = ByteBuffer.allocate(size);
            client.read(dataBuffer);
            dataBuffer.flip();
            return (Response) fromByteBuffer(dataBuffer);
        } catch (IOException | ClassNotFoundException exception) {
            return new Response(false, "Receive information error");
        }
    }

    /*
    Converting object into ByteBuffer
    @param object - serializable object
    @return return object converted in ByteBuffer
    @throws IOException input output exception
     */
    public static byte[] toBuffer(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        return baos.toByteArray();
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
