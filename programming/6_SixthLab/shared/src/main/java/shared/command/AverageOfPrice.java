package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CollectionHandler;
import shared.CommandHandler;

import java.util.Vector;

/**
 * Класс команды для вывода среднего значения поля цены всех элементов коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class AverageOfPrice extends UsableCommand implements RemoteCommand {

    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;
    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public AverageOfPrice(CommandHandler invoker) {
        super("Average_of_price", "Команда для вывода среднего значения поля цены всех элементов коллекции");
        this.invoker = invoker;
    }

    public AverageOfPrice() {
        this(null);
    }

    public void execute() {
        Vector<Ticket> collection = collectionHandler.getCollection();
        long sum = 0;
        for (Ticket ticket : collection) {
            sum += ticket.getPrice();
        }
        invoker.setFeedback(collection.isEmpty() ? "Коллекция пуста." : "Среднее значение цены в коллекции " + sum / collection.size());
    }
}
