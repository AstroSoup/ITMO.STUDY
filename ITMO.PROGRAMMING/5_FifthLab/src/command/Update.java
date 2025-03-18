package command;

import entity.*;
import utility.CollectionHandler;
import utility.CommandHandler;

import java.util.Objects;
import java.util.Vector;

/**
 * Класс команды для обновления данных элемента по его уникальному идентификатору.
 *
 * @author AstroSoup
 */
public class Update extends UsableCommand {

    final private StringifiedTicket ticket;
    final private int id;
    final private CommandHandler invoker;

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
        Vector<Ticket> collection = CollectionHandler.getHandler().getCollection();
        for (Ticket t : collection) {
            if (t.getId() == id) {
                t.setName(ticket.name().equals("-") ? t.getName() : ticket.name());

                t.setPrice(ticket.price().equals("-") ? t.getPrice() : Long.parseLong(ticket.price()));

                t.setDiscount(ticket.discount().equals("-") ? t.getDiscount() : Float.parseFloat(ticket.discount()));

                t.setCoordinates(new Coordinates(ticket.coordinates().x().equals("-") ? t.getCoordinates().getX() : Float.parseFloat(ticket.coordinates().x()),
                                ticket.coordinates().y().equals("-") ? t.getCoordinates().getY() : Double.parseDouble(ticket.coordinates().y())
                        )
                );

                t.setPerson(new Person(
                                ticket.person().passportID().equals("-") ? t.getPerson().getPassportID() : ticket.person().passportID(),
                                ticket.person().eyeColor().equals("-") ? t.getPerson().getEyeColor() : Color.valueOf(ticket.person().eyeColor()),
                                new Location(
                                        ticket.person().location().x().equals("-") ? t.getPerson().getLocation().getX() : Long.parseLong(ticket.person().location().x()),
                                        ticket.person().location().y().equals("-") ? t.getPerson().getLocation().getY() : Long.parseLong(ticket.person().location().y()),
                                        ticket.person().location().z().equals("-") ? t.getPerson().getLocation().getZ() : Long.parseLong(ticket.person().location().z())
                                )
                        )
                );

                t.setType(Objects.isNull(ticket.type()) ? null : (ticket.type().equals("-") ? t.getType() : TicketType.valueOf(ticket.type())));
                invoker.setFeedback("Билет изменён.");
                return;
            }
        }
    }
}
