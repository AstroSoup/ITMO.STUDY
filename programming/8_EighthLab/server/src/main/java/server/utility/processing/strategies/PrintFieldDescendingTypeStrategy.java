package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.UsableCommand;
import shared.entity.Ticket;
import shared.entity.TicketType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;

public class PrintFieldDescendingTypeStrategy implements CommandStrategy {
    public PrintFieldDescendingTypeStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {

        Vector<TicketType> result = new CollectionHandler().getCollection().stream().map(Ticket::getType).sorted(Comparator.nullsLast(Comparator.naturalOrder())).collect(Collectors.toCollection(Vector::new));

        ArrayList<String> resultStringList = result.stream().map(t -> {
            return t == null ? "На коленочках у проводника" : t.toString();
        }).collect(Collectors.toCollection(ArrayList::new));

        return new Response(true, resultStringList.isEmpty() ? "Коллекция пуста." : "Типы вагонов в билетах отсортированные по убыванию кошерности: " + String.join(", ", resultStringList));
    }
}
