package main.java.managers;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
                        // Attach buffer to key
                        try {
                            key.attach(ThreadExecuteManager.getInstance().makeActions(client));
                        } catch (InterruptedException exception) {
                            ResponseManager.getInstance().stopMachine();
                            RequestManager.getInstance().stopMachine();
                            ThreadExecuteManager.getInstance().stopMachine();
                            if (stop) return;
                        }
                        // Change interestops to write
                        key.interestOps(SelectionKey.OP_WRITE);

                    } else if (key.isWritable()) {
                        // You are ready to send result back to the client
                        SocketChannel client = (SocketChannel) key.channel();
                        Future<ByteBuffer> promiseResult = (Future<ByteBuffer>) key.attachment();
                        try {
                            if (promiseResult.isDone()) {
                                ByteBuffer buffer = promiseResult.get();
                                buffer.flip();
                                client.write(buffer);
                                client.close();
                            }
                        } catch (InterruptedException | ExecutionException exception) {
                            ResponseManager.getInstance().stopMachine();
                            RequestManager.getInstance().stopMachine();
                            ThreadExecuteManager.getInstance().stopMachine();
                            if (stop) return;
                        }
                    }
                    iterator.remove();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exception) {
                    ResponseManager.getInstance().stopMachine();
                    RequestManager.getInstance().stopMachine();
                    ThreadExecuteManager.getInstance().stopMachine();
                    if (stop) return;
                }
            }
        } catch (IOException e) {
            System.out.println("There was an error: " + e);
        }
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }
}
