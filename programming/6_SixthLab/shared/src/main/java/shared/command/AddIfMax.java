package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CollectionHandler;
import shared.CommandHandler;

import java.io.InvalidObjectException;
import java.util.Optional;

/**
 * Класс команды для добавления элемента в коллекцию, при условии что он больше всех элементов уже расположенных в коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class AddIfMax extends UsableCommand implements RemoteCommand {

    private Ticket ticket;
    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;
    /**
     * Конструктор
     *
     * @param ticket  Элемент добавляемый в коллекцию
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public AddIfMax(Ticket ticket, CommandHandler invoker) {
        super("Add_if_max", "Команда для добавления элемента в коллекцию, при условии что он больше всех элементов уже расположенных в коллекции. Каждое поле добавляемого элемента вводится на отдельной строке");
        this.ticket = ticket;
        this.invoker = invoker;
    }

    public AddIfMax() {
        this(null, null);
    }

    public void execute() {
        Optional<Ticket> t = collectionHandler.getCollection().stream().max(Ticket::compareTo);
        if (t.isPresent() && this.ticket.compareTo(t.get()) > 0) {
            try {
                collectionHandler.add(this.ticket);
                invoker.setFeedback("Билет добавлен в коллекцию.");
            } catch (InvalidObjectException e) {
                invoker.setFeedback(e.getMessage());
            }

            return;
        }
        invoker.setFeedback("Билет не является максимальным.");
    }
    @XmlElement
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
