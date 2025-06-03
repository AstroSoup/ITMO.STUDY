package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.UsableCommand;
import shared.entity.Ticket;

import java.util.Comparator;
import java.util.stream.Collectors;

public class ShowStrategy implements CommandStrategy {
    public ShowStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        return new Response(true, new CollectionHandler().getCollection().isEmpty() ? "Коллекция пуста." : new CollectionHandler().getCollection().stream().map(Ticket::toString).collect(Collectors.joining("\n")));
    }
}
