package main.java.utility;
import java.util.Scanner;

public interface Console {
    void print(Object obj);
    void println(Object obj);
    String readln();
    boolean isReadableln();
    void printError(Object obj);
    void printTable(Object obj1, Object obj2);
    void prompt();
    String getPrompt();
    void selectFileScanner(Scanner scn);
    void selectConsoleScanner();
    boolean hasNext();
    boolean hasNextLine();
}
