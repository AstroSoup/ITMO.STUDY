package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.UsableCommand;
import shared.entity.Ticket;

import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;

public class ReorderStrategy implements CommandStrategy {
    public ReorderStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        CollectionHandler ch = new CollectionHandler();
        ch.reorder();
        return new Response(true, ch.getCollection().isEmpty() ? "Коллекция пуста." : ch.getCollection().stream().map(Ticket::toString).collect(Collectors.joining("\n")));
    }
}
