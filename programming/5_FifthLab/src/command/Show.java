package command;

import entity.Ticket;
import utility.CollectionHandler;
import utility.CommandHandler;

import java.util.stream.Collectors;

/**
 * Класс команды для вывода всех элементов коллекции упорядоченных по возрастанию.
 *
 * @author AstroSoup
 */
public class Show extends UsableCommand {

    final private CommandHandler invoker;

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
        CollectionHandler.getHandler().sort();
        String rst = CollectionHandler.getHandler().getCollection().stream().map(Ticket::toString).collect(Collectors.joining("\n"));
        invoker.setFeedback(rst.isEmpty() ? "Коллекция пуста." : rst);
    }
}
