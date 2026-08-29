package ru.astrosoup.weblab3.exceptions;

public class InvalidJwtException extends Exception {
    public InvalidJwtException(String message) {
        super(message);
    }
}
