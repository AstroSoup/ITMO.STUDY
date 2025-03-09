package command;

import entity.Ticket;
import utility.CollectionHandler;
import utility.CommandHandler;

import java.util.stream.Collectors;
/**
 * Класс команды для вывода всех значений элементов коллекции упорядоченных по убыванию.
 *
 * @author AstroSoup
 */
public class Reorder extends UsableCommand {

    final private CommandHandler invoker;
    /**
     * Конструктор
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Reorder(CommandHandler invoker) {
        super("Reorder", "Команда для вывода всех значений элементов коллекции упорядоченных по убыванию");
        this.invoker = invoker;
    }

    public void execute() {
        CollectionHandler.getHandler().reorder();
        invoker.setFeedback(CollectionHandler.getHandler().getCollection().isEmpty() ? "Коллекция пуста." : CollectionHandler.getHandler().getCollection().stream().map(Ticket::toString).collect(Collectors.joining("\n")));
    }
}
