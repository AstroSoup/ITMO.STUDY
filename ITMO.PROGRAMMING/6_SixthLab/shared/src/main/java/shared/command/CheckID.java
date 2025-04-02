package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CollectionHandler;
import shared.CommandHandler;

/**
 * Класс команды для проверки наличия элемента с некоторым уникальным идентификатором в коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class CheckID implements Command, RemoteCommand {

    private CommandHandler invoker;
    private int id;
    CollectionHandler collectionHandler = null;
    /**
     * Конструктор
     *
     * @param id      Уникальный идентификатор, наличие которого проверяет команда
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public CheckID(int id, CommandHandler invoker) {
        this.id = id;
        this.invoker = invoker;
    }
    public CheckID() {
        this.id = -1;
        this.invoker = null;
    }


    public void execute() {
        for (Ticket t : collectionHandler.getCollection()) {
            if (t.getId() == this.id) {
                invoker.setFeedback("1");
                return;
            }
        }
        invoker.setFeedback("");
    }
    @XmlElement
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
