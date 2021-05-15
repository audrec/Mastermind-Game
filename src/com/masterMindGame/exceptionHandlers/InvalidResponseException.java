package com.masterMindGame.exceptionHandlers;

public class InvalidResponseException extends Exception{
    public InvalidResponseException(String message) {
        super(message);
    }
}
