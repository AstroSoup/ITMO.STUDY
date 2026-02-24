package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CollectionHandler;
import shared.CommandHandler;

import java.util.Comparator;
import java.util.Vector;

/**
 * Класс команды для вывода мета-информации о коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Info extends UsableCommand implements RemoteCommand {

    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;
    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Info(CommandHandler invoker) {
        super("Info", "Команда для получения мета-информации о коллекции");
        this.invoker = invoker;
    }

    public Info() {
        this(null);
    }

    public void execute() {
        Vector<Ticket> collection = collectionHandler.getCollection();

        invoker.setFeedback("Время создания: " + collectionHandler.getCDT() + "\n"
                + "Тип коллекции: " + collection.getClass().getCanonicalName() + "\n"
                + "Количество элементов в коллекции: " + collection.size() + "\n"
                + (collection.isEmpty() ? "" : (
                "Самый дорогой билет оформлен на имя: " + collection.stream().max(Comparator.comparing(Ticket::getPrice)).get().getName() + "\n"
                        + "Самый дешёвый билет оформлен на имя: " + collection.stream().min(Comparator.comparing(Ticket::getPrice)).get().getName()) + "\n")
        );
    }
}
