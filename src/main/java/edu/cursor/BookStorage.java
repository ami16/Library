package edu.cursor;

public class BookStorage {

    public void run() {

        BookFunctions func = new BookFunctions();

        while (true) {
            func.showMainMenu();
            func.getMainChoice();
        }
    }
}