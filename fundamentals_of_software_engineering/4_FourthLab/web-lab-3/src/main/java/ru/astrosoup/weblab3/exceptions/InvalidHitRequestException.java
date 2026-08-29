package ru.astrosoup.weblab3.exceptions;

public class InvalidHitRequestException extends RuntimeException {
    public InvalidHitRequestException(String message) {
        super(message);
    }
}
