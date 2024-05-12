package main.java.commands;

import java.io.Serial;
import java.io.Serializable;

public abstract class Command implements Creatable, Describable, Serializable {

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

    public Boolean orderToServer() {
        return this.isServerMethod;
    }

    public Boolean getNeedCommandManager() {
        return this.needCommandManager;
    }

    public Boolean getNeedCollectionManager() {
        return this.needCollectionManager;
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
