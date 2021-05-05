package com.masterMindGame.exceptionHandlers;

public class InvalidMethodCallException extends RuntimeException {
    public InvalidMethodCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
