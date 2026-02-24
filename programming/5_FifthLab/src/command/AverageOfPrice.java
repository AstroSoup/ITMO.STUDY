package command;

import entity.Ticket;
import utility.CollectionHandler;
import utility.CommandHandler;

import java.util.Vector;

/**
 * Класс команды для вывода среднего значения поля цены всех элементов коллекции.
 *
 * @author AstroSoup
 */
public class AverageOfPrice extends UsableCommand {

    final private CommandHandler invoker;

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
        Vector<Ticket> collection = CollectionHandler.getHandler().getCollection();
        long sum = 0;
        for (Ticket ticket : collection) {
            sum += ticket.getPrice();
        }
        invoker.setFeedback(collection.isEmpty() ? "Коллекция пуста." : "Среднее значение цены в коллекции " + sum / collection.size());
    }
}
