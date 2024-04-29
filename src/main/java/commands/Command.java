package main.java.commands;

import main.java.managers.CollectionManager;
import main.java.managers.CommandManager;

import java.io.Serial;
import java.io.Serializable;

public abstract class Command implements Executable, Describable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String description;
    private final Boolean needCommandManager;
    private final Boolean needCollectionManager;
    private final Boolean isServerMethod;

    public Command(String name, String description, Boolean needCommandManager, Boolean needCollectionManager, Boolean isServerMethod) {
        this.name = name;
        this.description = description;
        this.needCommandManager = needCommandManager;
        this.needCollectionManager = needCollectionManager;
        this.isServerMethod = isServerMethod;
    }

    public Boolean getNeedCollectionManager() {
        return this.needCollectionManager;
    }

    public Boolean getNeedCommandManager() {
        return this.needCommandManager;
    }

    abstract public void setCollectionManager(CollectionManager collectionManager);
    abstract public void setCommandManager(CommandManager commandManager);

    public Boolean orderToServer() {
        return this.isServerMethod;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(((Command) obj).description);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    @Override
    public String toString() {
        return "Command{name: " + name + ", description: " + description + "}";
    }
}
