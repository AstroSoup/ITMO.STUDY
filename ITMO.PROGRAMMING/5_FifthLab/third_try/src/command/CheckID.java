package command;

import entity.Ticket;
import utility.CollectionHandler;
import utility.CommandHandler;
/**
 * Класс команды для проверки наличия элемента с некоторым уникальным идентификатором в коллекции.
 *
 * @author AstroSoup
 */
public class CheckID implements Command{

    final private CommandHandler invoker;
    final private int id;
    /**
     * Конструктор
     * @param id Уникальный идентификатор, наличие которого проверяет команда
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public CheckID(int id, CommandHandler invoker) {
        this.id = id;
        this.invoker = invoker;
    }

    public void execute() {
        for (Ticket t : CollectionHandler.getHandler().getCollection()) {
            if (t.getId() == this.id) {
                invoker.setFeedback("1");
                return;
            }
        }
        invoker.setFeedback("");
    }
}
