package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.*;
import shared.CollectionHandler;
import shared.CommandHandler;

import java.util.stream.Collectors;

/**
 * Класс команды для вывода всех значений элементов коллекции упорядоченных по убыванию.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Reorder extends UsableCommand implements RemoteCommand {

    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;
    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Reorder(CommandHandler invoker) {
        super("Reorder", "Команда для вывода всех значений элементов коллекции упорядоченных по убыванию");
        this.invoker = invoker;
    }

    public Reorder() {
        this(null);
    }

    public void execute() {
        collectionHandler.reorder();
        invoker.setFeedback(collectionHandler.getCollection().isEmpty() ? "Коллекция пуста." : collectionHandler.getCollection().stream().map(Ticket::toString).collect(Collectors.joining("\n")));
    }
}
