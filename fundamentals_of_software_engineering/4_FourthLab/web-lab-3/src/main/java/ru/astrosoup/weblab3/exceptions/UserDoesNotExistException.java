package ru.astrosoup.weblab3.exceptions;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
