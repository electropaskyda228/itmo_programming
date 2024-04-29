package main.java.utility;

import java.io.Serial;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StandartConsole implements Console, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String S = "> ";
    private static Scanner fileScanner = null;
    private static Scanner defScanner = new Scanner(System.in);

    public void print(Object obj) {
        System.out.print(obj);
    }

    public void println(Object obj) {
        System.out.println(obj);
    }

    public void printError(Object obj) {
        System.out.println("Error: " + obj);
    }

    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner != null ? fileScanner:defScanner).nextLine();
    }

    public boolean isReadableln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner:defScanner).hasNextLine();
    }

    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    public void prompt() {
        print(S);
    }

    public String getPrompt() {
        return S;
    }

    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }

    public void selectConsoleScanner() {
        fileScanner = null;
    }
    public boolean hasNext() {
        return (fileScanner != null ? fileScanner:defScanner).hasNext();
    }
    public boolean hasNextLine() {
        return (fileScanner != null ? fileScanner:defScanner).hasNextLine();
    }
}
