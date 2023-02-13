package com.booking.booking.exception;

public class NoSuchObjectException extends RuntimeException{
    public NoSuchObjectException(String message){
        super(message);
    }
    public NoSuchObjectException(String message, Throwable cause){
        super(message, cause);
    }

    public NoSuchObjectException(Throwable cause){
        super(cause);
    }
}
