package main.java.managers;

import main.java.commands.Command;
import main.java.utility.StandartConsole;
import main.java.utility.Console;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ConnectionManager extends Thread {
    private static final String host = "localhost";
    private static final int port = 6789;
    private Boolean stop = false;


    private ConnectionManager() {
    }

    private static class ConnectionManagerHolder {
        private static final ConnectionManager HOLDER_INSTANCE = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        return ConnectionManagerHolder.HOLDER_INSTANCE;
    }

    public void run() {
        try {
            Console console = new StandartConsole();
            console.println("Enter 'exit' to save collection and get out");
            console.print(console.getPrompt());
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        // Client connected
                        SocketChannel client = serverSocket.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        Command receivedObject = (Command) ReadManager.getInstance().read(client);
                        // Serialize result back into buffer
                        ByteBuffer bufferToWrite = ResponseManager.getInstance().makeResponse(RequestManager.getInstance().getRequest(receivedObject));
                        // Attach buffer to key
                        key.attach(bufferToWrite);
                        // Change interestops to write
                        key.interestOps(SelectionKey.OP_WRITE);

                    } else if (key.isWritable()) {
                        // You are ready to send result back to the client
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        buffer.flip();
                        client.write(buffer);
                        client.close();
                    }
                    iterator.remove();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exception) {
                    CollectionManager.getInstance().saveCollection();
                    if (stop) break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("There was an error: " + e);
        }
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }
}
