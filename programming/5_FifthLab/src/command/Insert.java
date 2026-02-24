package command;

import entity.Ticket;
import utility.CollectionHandler;
import utility.CommandHandler;

import java.io.InvalidObjectException;

/**
 * Класс команды для добавления элемента в коллекцию по индексу.
 *
 * @author AstroSoup
 */
public class Insert extends UsableCommand {

    final private int index;
    final private Ticket ticket;
    final private CommandHandler invoker;

    /**
     * Конструктор
     *
     * @param ticket  Элемент который необходимо вставить
     * @param index   Индекс в коллекции по которому нужно вставить элемент
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Insert(int index, Ticket ticket, CommandHandler invoker) {
        super("Insert_at", "Команда для добавления элемента в коллекцию по индексу. Индекс необходимо указать на той же строке, что и имя команды. Каждое поле добавляемого элемента вводится на отдельной строке");
        this.index = index;
        this.ticket = ticket;
        this.invoker = invoker;
    }

    public Insert() {
        this(-1, null, null);
    }

    public void execute() {
        try {
            CollectionHandler.getHandler().insert(index, ticket);
            invoker.setFeedback("Билет добавлен в коллекцию по индексу " + index + ".");
        } catch (InvalidObjectException e) {
            invoker.setFeedback(e.getMessage());
        }

    }
}
