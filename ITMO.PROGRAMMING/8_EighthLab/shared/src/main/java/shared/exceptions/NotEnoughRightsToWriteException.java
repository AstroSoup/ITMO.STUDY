package shared.exceptions;

/**
 * Класс - исключение, выбрасывается при недостаточных правах для записи
 *
 * @author AstroSoup
 */
public class NotEnoughRightsToWriteException extends Exception {
    public NotEnoughRightsToWriteException() {
    }

    @Override
    public String getMessage() {
        return "Файл недоступен для записи.";
    }
}
