package command;

import entity.Ticket;
import utility.CollectionHandler;
import utility.CommandHandler;

/**
 * Класс команды для удаления элемента из коллекции по его уникальному идентификатору.
 *
 * @author AstroSoup
 */
public class RemoveById extends UsableCommand {

    final private int id;
    final private CommandHandler invoker;

    /**
     * Конструктор
     *
     * @param id      Уникальный идентификатор элемента который необходимо удалить из коллекции
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public RemoveById(int id, CommandHandler invoker) {
        super("Remove_by_id", "Команда для удаления элемента из коллекции по его уникальному идентификатору. Уникальный идентификатор необходимо указать на той же строке, что и имя команды");
        this.id = id;
        this.invoker = invoker;
    }

    public RemoveById() {
        this(-1, null);
    }

    public void execute() {
        for (Ticket ticket : CollectionHandler.getHandler().getCollection()) {
            if (ticket.getId() == this.id) {
                CollectionHandler.getHandler().remove(ticket);
                invoker.setFeedback("Билет убран из коллекции.");
                return;
            }
        }
        invoker.setFeedback("Билет с данным ID отсутствует в коллекции.");
    }
}
