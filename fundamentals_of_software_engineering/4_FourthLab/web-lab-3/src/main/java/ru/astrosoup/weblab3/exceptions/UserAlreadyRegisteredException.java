package ru.astrosoup.weblab3.exceptions;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
