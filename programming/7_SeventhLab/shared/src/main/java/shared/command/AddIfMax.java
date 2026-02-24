package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
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
    /**
     * Конструктор
     *
     * @param ticket  Элемент добавляемый в коллекцию
     */
    public AddIfMax(Ticket ticket, CommandHandler invoker) {
        super("Add_if_max", "Команда для добавления элемента в коллекцию, при условии что он больше всех элементов уже расположенных в коллекции. Каждое поле добавляемого элемента вводится на отдельной строке");
        this.ticket = ticket;
        this.setInvoker(invoker);
    }

    public AddIfMax() {
        this(null, null);
    }

    @XmlElement
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
