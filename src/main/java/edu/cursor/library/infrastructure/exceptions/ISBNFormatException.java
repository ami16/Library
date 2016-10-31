package edu.cursor.library.infrastructure.exceptions;

public class ISBNFormatException extends Exception {
    private String value;

    public String getValue() {
        return value;
    }

    public ISBNFormatException(String message, String num) {

        super(message + num);
        value = num;
    }
}


