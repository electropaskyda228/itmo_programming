package main.java.commands;

import main.java.models.User;

import java.io.Serial;
import java.io.Serializable;

public abstract class Command implements Creatable, Describable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String description;
    private User user = null;
    private Boolean isServerMethod = false;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Boolean orderToServer() {
        return this.isServerMethod;
    }
    protected void setIsServerMethod(boolean isServerMethod) {
        this.isServerMethod = isServerMethod;
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
