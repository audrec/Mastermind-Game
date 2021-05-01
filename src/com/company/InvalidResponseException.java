package com.company;

public class InvalidResponseException extends Exception{
    public InvalidResponseException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidResponseException(String message) {
        super(message);
    }
}
