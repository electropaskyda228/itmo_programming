package main.java.managers;

import main.java.commands.Command;
import main.java.utility.Response;

import java.io.Serial;
import java.io.Serializable;

public class RequestManager {
    public Response getRequest(CommandManager commandManager, CollectionManager collectionManager, Command receivedObject) {
        if (receivedObject.getNeedCollectionManager()) receivedObject.setCollectionManager(collectionManager);
        if (receivedObject.getNeedCommandManager()) receivedObject.setCommandManager(commandManager);
        commandManager.addToHistory(receivedObject.getName());
        return receivedObject.apply();
    }
}
