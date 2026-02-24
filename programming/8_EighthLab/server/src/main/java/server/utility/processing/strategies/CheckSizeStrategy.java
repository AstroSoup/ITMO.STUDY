package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.CheckSize;
import shared.command.UsableCommand;

public class CheckSizeStrategy implements CommandStrategy {
    public CheckSizeStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        CheckSize checkSize = (CheckSize) cmd;
        if (checkSize.getIndex() >= 0 && checkSize.getIndex() <= new CollectionHandler().getCollection().size()) {
            return new Response(true, "1");
        }
        return new Response(true, "");
    }
}
