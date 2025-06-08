package shared.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;
import java.util.Vector;

/**
 * Класс-оболочка для сериализации коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketVectorWrapper implements Serializable {
    @XmlElement
    private Vector<Ticket> tickets;
    @XmlElement
    private long version;

    public TicketVectorWrapper() {
        this.tickets = new Vector<>();
    }

    /**
     * Конструктор
     *
     * @param tickets    Вектор содержащий объекты класса Ticket
     */
    public TicketVectorWrapper(Vector<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Vector<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Vector<Ticket> tickets) {
        this.tickets = tickets;
    }
    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }
}