package shared.exceptions;

/**
 * Класс - исключение, выбрасывается когда на вход команде передано слишком много аргументов
 */
public class TooMuchArgumentsException extends Exception {
    private int neededArgs;
    private int actualArgs;

    /**
     * Конструктор
     *
     * @param neededArgs необходимое количество аргументов
     * @param actualArgs переданное количество аргументов
     */
    public TooMuchArgumentsException(int neededArgs, int actualArgs) {
        this.neededArgs = neededArgs;
        this.actualArgs = actualArgs;
    }

    @Override
    public String getMessage() {
        return "Слишком много аргументов. Ожидалось " + neededArgs + " аргументов, получено " + actualArgs + ".";
    }
}
