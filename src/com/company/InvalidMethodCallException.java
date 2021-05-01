package com.company;

public class InvalidMethodCallException extends RuntimeException {
    public InvalidMethodCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
