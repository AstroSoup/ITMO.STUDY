package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.*;
import shared.CollectionHandler;
import shared.CommandHandler;

/**
 * Класс команды для удаления элемента из коллекции по его уникальному идентификатору.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class RemoveById extends UsableCommand implements RemoteCommand {

    private int id;
    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;
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
        for (Ticket ticket : collectionHandler.getCollection()) {
            if (ticket.getId() == this.id) {
                collectionHandler.remove(ticket);
                invoker.setFeedback("Билет убран из коллекции.");
                return;
            }
        }
        invoker.setFeedback("Билет с данным ID отсутствует в коллекции.");
    }
    @XmlElement
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
