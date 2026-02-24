package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.UsableCommand;
import shared.entity.Ticket;

import java.util.Comparator;

public class InfoStrategy implements CommandStrategy {
    public InfoStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        CollectionHandler ch = new CollectionHandler();
        return new Response(true, "Тип коллекции: " + ch.getCollection().getClass().getCanonicalName() + "\n"
                + "Количество элементов в коллекции: " + ch.getCollection().size() + "\n"
                + (ch.getCollection().isEmpty() ? "" : (
                "Самый дорогой билет оформлен на имя: " + ch.getCollection().stream().max(Comparator.comparing(Ticket::getPrice)).get().getName() + "\n"
                        + "Самый дешёвый билет оформлен на имя: " + ch.getCollection().stream().min(Comparator.comparing(Ticket::getPrice)).get().getName()) + "\n")
        );
    }
}
