package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.AverageOfPrice;
import shared.command.UsableCommand;
import shared.entity.Ticket;

public class AverageOfPriceStrategy implements CommandStrategy {
    public AverageOfPriceStrategy() {}

    public Response execute(UsableCommand cmd) {
        CollectionHandler ch = new CollectionHandler();
        return new Response(true,
                String.valueOf(ch.getCollection().stream().map(Ticket::getPrice).mapToLong(x -> x.longValue()).sum()
                        / ch.getCollection().size())
        );

    }
}
