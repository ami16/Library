package edu.cursor.library.infrastructure.exception;


public class NoSuchBookException extends Exception{
    private String val;
    public String getVal(){return val;}
    public NoSuchBookException(String message, String val){

        super(message + val);
        val=val;
    }
}
