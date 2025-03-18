package exceptions;

/**
 * Класс - исключение, выбрасывается при вводе невалидной команды
 */
public class UnknownCommandException extends Exception {
    private String name;

    /**
     * Конструктор
     *
     * @param name введенная невалидная комманда
     */
    public UnknownCommandException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Команда '" + name + "' невалидна, напишите help чтобы узнать больше.";
    }
}
