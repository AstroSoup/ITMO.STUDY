package entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Vector;

/**
 * Класс-оболочка для сериализации коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class TicketVectorWrapper implements Serializable {
    private Vector<Ticket> tickets;
    private String creationDT;

    public TicketVectorWrapper() {
        this.tickets = new Vector<>();
    }

    /**
     * Конструктор
     *
     * @param tickets    Вектор содержащий объекты класса Ticket
     * @param creationDT дата создания коллекции в строковом представлении
     */
    public TicketVectorWrapper(Vector<Ticket> tickets, String creationDT) {
        this.tickets = tickets;
        this.creationDT = creationDT;
    }

    @XmlElement
    public Vector<Ticket> getTickets() {
        return tickets;
    }

    @XmlElement
    public String getCDT() {
        return creationDT;
    }

    public void setCDT(String creationDT) {
        this.creationDT = creationDT;
    }
}