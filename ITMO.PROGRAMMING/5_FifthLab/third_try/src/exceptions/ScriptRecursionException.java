package exceptions;


/**
 * Класс - исключение, выбрасывается при попытке вызвать на выполнение файл, выполнение которого еще не завершено.
 *
 * @author AstroSoup
 */
public class ScriptRecursionException extends Exception {
    public ScriptRecursionException() {
    }
    @Override
    public String getMessage() {
        return "Выполнение скрипта прервано. Обнаружена рекурсия. Если файл уже был вызван, его нельзя вызвать из другого скрипта до завершения текущего.";
    }
}
