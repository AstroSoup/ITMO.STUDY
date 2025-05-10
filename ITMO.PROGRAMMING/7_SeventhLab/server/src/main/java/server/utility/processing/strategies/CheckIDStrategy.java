package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.CheckID;
import shared.command.UsableCommand;
import shared.entity.Ticket;

public class CheckIDStrategy implements CommandStrategy {
    public CheckIDStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        CheckID checkID = (CheckID) cmd;
        for (Ticket t : new CollectionHandler().getCollection()) {
            if (t.getId() == checkID.getId()) {
                return new Response(true, "1");
            }
        }
        return new Response(true, "");
    }
}
