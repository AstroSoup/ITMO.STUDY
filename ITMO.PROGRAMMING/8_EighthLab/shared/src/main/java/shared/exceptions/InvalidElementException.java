package shared.exceptions;

public class InvalidElementException extends Exception {
    public InvalidElementException() {
    }

    public String getMessage() {
        return "Элемент не соответствует требованиям.";
    }
}
