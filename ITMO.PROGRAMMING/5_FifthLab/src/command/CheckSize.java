package command;

import utility.CollectionHandler;
import utility.CommandHandler;

/**
 * Класс команды для проверки размера коллекции.
 *
 * @author AstroSoup
 */
public class CheckSize implements Command {
    final private CommandHandler invoker;
    final private int index;

    /**
     * Конструктор
     *
     * @param index   индекс для которого осуществляется проверка размера коллекции
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public CheckSize(int index, CommandHandler invoker) {
        this.index = index;
        this.invoker = invoker;
    }

    public void execute() {
        if (index >= 0 && index <= CollectionHandler.getHandler().getCollection().size()) {
            invoker.setFeedback("1");
            return;
        }
        invoker.setFeedback("");
    }
}
