package command;

import entity.Ticket;
import utility.CollectionHandler;
import utility.CommandHandler;

import java.io.InvalidObjectException;

/**
 * Класс команды для добавления элемента в коллекцию.
 *
 * @author AstroSoup
 */
public class Add extends UsableCommand {

    final private Ticket ticket;
    final private CommandHandler invoker;
    /**
     * Конструктор
     * @param ticket Элемент добавляемый в коллекцию
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Add(Ticket ticket,CommandHandler invoker) {
        super("Add", "Команда для добавления элемента в коллекцию. Каждое поле добавляемого элемента вводится на отдельной строке");
        this.ticket = ticket;
        this.invoker = invoker;
    }

    public void execute() {
        try {
            CollectionHandler.getHandler().add(ticket);
            invoker.setFeedback("Билет добавлен в коллекцию.");
        } catch (InvalidObjectException e) {
            invoker.setFeedback(e.getMessage());
        }

    }
}
