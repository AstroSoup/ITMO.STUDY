package command;

import entity.Ticket;
import utility.CollectionHandler;
import utility.CommandHandler;

import java.util.Comparator;
import java.util.Vector;
/**
 * Класс команды для вывода мета-информации о коллекции.
 *
 * @author AstroSoup
 */
public class Info extends UsableCommand {

    final private CommandHandler invoker;
    /**
     * Конструктор
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Info(CommandHandler invoker) {
        super("Info", "Команда для получения мета-информации о коллекции");
        this.invoker = invoker;
    }

    public void execute() {
        Vector<Ticket> collection = CollectionHandler.getHandler().getCollection();

        invoker.setFeedback("Время создания: " + CollectionHandler.getHandler().getCDT() + "\n"
                + "Тип коллекции: " + collection.getClass().getCanonicalName() + "\n"
                + "Количество элементов в коллекции: " + collection.size() + "\n"
                + (collection.isEmpty() ? "" : (
                        "Самый дорогой билет оформлен на имя: " +  collection.stream().max(Comparator.comparing(Ticket::getPrice)).get().getName() + "\n"
                        + "Самый дешёвый билет оформлен на имя: " + collection.stream().min(Comparator.comparing(Ticket::getPrice)).get().getName()) + "\n")
        );
    }
}
