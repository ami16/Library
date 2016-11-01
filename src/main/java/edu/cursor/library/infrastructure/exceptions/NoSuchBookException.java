package edu.cursor.library.infrastructure.exceptions;

/**
 * Created by Root-UA on 2016-10-28.
 */
public class NoSuchBookException extends Exception{
    private int number;
    public int getNumber(){return number;}
    public NoSuchBookException(String message, int num){

        super(message);
        number=num;
    }
}
