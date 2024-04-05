package main.java.utility;

public class Response {
    private boolean exitCode;
    private String message;

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
