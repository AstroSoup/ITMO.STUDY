package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.Update;
import shared.command.UsableCommand;
import shared.entity.*;

import java.util.Objects;
import java.util.Vector;

public class UpdateStrategy implements CommandStrategy {
    public UpdateStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        Update update = (Update) cmd;
        CollectionHandler ch = new CollectionHandler();
        Vector<Ticket> collection = ch.getCollection();
        StringifiedTicket ticket = update.getTicket();
        for (Ticket t : collection) {
            if (t.getId() == update.getId()) {
                if (t.getCreator().equals(update.getUsername())) {
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
                    try {
                        ch.update(t);
                    } catch (Exception e) {
                        return new Response(false, e.getMessage());
                    }
                    return new Response(true, "Билет изменён.");
                } else {
                    return new Response(true, "Данный билет принадлежит другому пользователю. Вы не можете его редактировать.");
                }
            }
        }
        return new Response(true, "Билет с таким id не найден в коллекции.");
    }
}
