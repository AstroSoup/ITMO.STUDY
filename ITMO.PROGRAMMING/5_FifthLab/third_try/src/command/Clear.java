package command;

import utility.CollectionHandler;
import utility.CommandHandler;
/**
 * Класс команды для очистки коллекции.
 *
 * @author AstroSoup
 */
public class Clear extends UsableCommand {
    final private CommandHandler invoker;
    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Clear(CommandHandler invoker) {
        super("Clear","Команда для очистки коллекции");
        this.invoker = invoker;
    }

    public void execute() {
        CollectionHandler.getHandler().clear();
        invoker.setFeedback("Коллекция очищена.");
    }
}
