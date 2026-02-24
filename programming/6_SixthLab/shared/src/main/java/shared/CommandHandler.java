package shared;

import shared.command.Command;
import shared.exceptions.ConnectionLostException;

/**
 * Класс, реализующий взаимодействие между CLI и коллекцией.
 *
 * @author AstroSoup
 */
public class CommandHandler {

    private String feedback = "";

    public CommandHandler() {
    }

    /**
     * Метод для вызова комманды.
     *
     * @param command экземпляр класса реализующего интерфейс Command
     */
    public void invoke(Command command) throws ConnectionLostException {
        command.execute();
    }

    /**
     * Метод для установки значения поля feedback после выполнения команды.
     *
     * @param feedback строка которая будет присвоена полю feedback
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Метод для получения фидбека по выполнению команды.
     *
     * @return Строка характеризующая результат выполнения команды
     */
    public String getFeedback() {
        return feedback;
    }
}
