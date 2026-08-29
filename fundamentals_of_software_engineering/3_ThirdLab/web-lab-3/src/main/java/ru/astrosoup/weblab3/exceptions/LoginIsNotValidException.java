package ru.astrosoup.weblab3.exceptions;

public class LoginIsNotValidException extends Exception {
    public LoginIsNotValidException(String message) {
        super(message);
    }
}
