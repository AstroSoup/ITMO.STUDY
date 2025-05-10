package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CommandHandler;

import java.io.InvalidObjectException;

/**
 * Класс команды для добавления элемента в коллекцию.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Add extends UsableCommand implements RemoteCommand {

    private Ticket ticket;


    /**
     * Конструктор
     *
     * @param ticket  Элемент добавляемый в коллекцию
     */

    public Add(Ticket ticket, CommandHandler invoker){
        super("Add", "Команда для добавления элемента в коллекцию. Каждое поле добавляемого элемента вводится на отдельной строке");
        this.ticket = ticket;
        this.setInvoker(invoker);
    }

    public Add() {
        this(null, null);
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    @XmlElement
    public Ticket getTicket() {
        return ticket;
    }
}
