package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.CollectionHandler;
import shared.CommandHandler;

/**
 * Класс команды для проверки размера коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class CheckSize implements Command, RemoteCommand {
    private CommandHandler invoker;
    private int index;
    CollectionHandler collectionHandler = null;
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
    public CheckSize() {
        this.index = -1;
        this.invoker = null;
    }

    public void execute() {
        if (index >= 0 && index <= collectionHandler.getCollection().size()) {
            invoker.setFeedback("1");
            return;
        }
        invoker.setFeedback("");
    }
    @XmlElement
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
