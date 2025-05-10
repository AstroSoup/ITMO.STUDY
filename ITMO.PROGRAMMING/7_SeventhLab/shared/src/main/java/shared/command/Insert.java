package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CommandHandler;

import java.io.InvalidObjectException;

/**
 * Класс команды для добавления элемента в коллекцию по индексу.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Insert extends UsableCommand implements RemoteCommand {

    private int index;
    private Ticket ticket;
    private CommandHandler invoker;
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


    @XmlElement
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    @XmlElement
    public Ticket getTicket() {
        return ticket;
    }
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
