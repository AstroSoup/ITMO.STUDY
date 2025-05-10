package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.UsableCommand;

public class ClearStrategy implements CommandStrategy {
    public ClearStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        new CollectionHandler().clear();
        return new Response(true, "Коллекция очищена.");
    }
}
