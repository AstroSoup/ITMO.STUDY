package ru.astrosoup.weblab3.exceptions;

public class JwtGenerationException extends Exception {
    public JwtGenerationException(String message) {
        super(message);
    }
}
