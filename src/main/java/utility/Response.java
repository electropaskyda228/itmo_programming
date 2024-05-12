package main.java.utility;

import java.io.Serial;
import java.io.Serializable;

public class Response implements Serializable {
    private boolean exitCode;
    private String message;
    @Serial
    private static final long serialVersionUID = 1L;

    public Response(boolean code, String message) {
        exitCode = code;
        this.message = message;
    }
    public Response(String message) {
        this.message = message;
    }

    public boolean getExitCode() { return exitCode; }
    public String getMessage() { return message; }
    public String toString() { return String.valueOf(exitCode)+";"+message; }
}
