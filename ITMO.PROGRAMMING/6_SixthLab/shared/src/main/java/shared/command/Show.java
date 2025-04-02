package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CollectionHandler;
import shared.CommandHandler;

import java.util.stream.Collectors;

/**
 * Класс команды для вывода всех элементов коллекции упорядоченных по возрастанию.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Show extends UsableCommand implements RemoteCommand {

    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;

    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Show(CommandHandler invoker) {
        super("Show", "Команда для вывода всех элементов коллекции упорядоченных по возрастанию");
        this.invoker = invoker;
    }

    public Show() {
        this(null);
    }

    public void execute() {
        collectionHandler.sort();
        String rst = collectionHandler.getCollection().stream().map(Ticket::toString).collect(Collectors.joining("\n"));

        invoker.setFeedback(rst.isEmpty() ? "Коллекция пуста." : rst);
    }
}
