package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.*;
import shared.CollectionHandler;
import shared.CommandHandler;

import java.util.Objects;
import java.util.Vector;

/**
 * Класс команды для обновления данных элемента по его уникальному идентификатору.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Update extends UsableCommand implements RemoteCommand {

    private StringifiedTicket ticket;
    private int id;
    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;

    /**
     * Конструктор
     *
     * @param ticket  Элемент
     * @param id      индекс в коллекции по которому нужно вставить элемент
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Update(int id, StringifiedTicket ticket, CommandHandler invoker) {
        super("Update", "Команда для обновления данных элемента по его уникальному идентификатору. Уникальный идентификатор необходимо указать на той же строке, что и имя команды. Каждое поле добавляемого элемента вводится на отдельной строке");
        this.id = id;
        this.ticket = ticket;
        this.invoker = invoker;
    }

    public Update() {
        this(-1, null, null);
    }

    public void execute() {
        Vector<Ticket> collection = collectionHandler.getCollection();
        for (Ticket t : collection) {
            if (t.getId() == id) {
                t.setName(ticket.getName().equals("-") ? t.getName() : ticket.getName());

                t.setPrice(ticket.getPrice().equals("-") ? t.getPrice() : Long.parseLong(ticket.getPrice()));

                t.setDiscount(ticket.getDiscount().equals("-") ? t.getDiscount() : Float.parseFloat(ticket.getDiscount()));

                t.setCoordinates(new Coordinates(ticket.getCoordinates().getX().equals("-") ? t.getCoordinates().getX() : Float.parseFloat(ticket.getCoordinates().getX()),
                                ticket.getCoordinates().getY().equals("-") ? t.getCoordinates().getY() : Double.parseDouble(ticket.getCoordinates().getY())
                        )
                );

                t.setPerson(new Person(
                                ticket.getPerson().getPassportID().equals("-") ? t.getPerson().getPassportID() : ticket.getPerson().getPassportID(),
                                ticket.getPerson().getEyeColor().equals("-") ? t.getPerson().getEyeColor() : Color.valueOf(ticket.getPerson().getEyeColor()),
                                new Location(
                                        ticket.getPerson().getLocation().getX().equals("-") ? t.getPerson().getLocation().getX() : Long.parseLong(ticket.getPerson().getLocation().getX()),
                                        ticket.getPerson().getLocation().getY().equals("-") ? t.getPerson().getLocation().getY() : Long.parseLong(ticket.getPerson().getLocation().getY()),
                                        ticket.getPerson().getLocation().getZ().equals("-") ? t.getPerson().getLocation().getZ() : Long.parseLong(ticket.getPerson().getLocation().getZ())
                                )
                        )
                );

                t.setType(Objects.isNull(ticket.getType()) ? null : (ticket.getType().equals("-") ? t.getType() : TicketType.valueOf(ticket.getType())));
                invoker.setFeedback("Билет изменён.");
                return;
            }
        }
    }
    @XmlElement
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public StringifiedTicket getTicket() {
        return ticket;
    }
    public void setTicket(StringifiedTicket ticket) {
        this.ticket = ticket;
    }
}
