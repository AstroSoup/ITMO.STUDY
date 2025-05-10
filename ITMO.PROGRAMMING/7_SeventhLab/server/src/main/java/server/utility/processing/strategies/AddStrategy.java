package server.utility.processing.strategies;


import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.Add;
import shared.command.UsableCommand;
import shared.entity.Ticket;

import java.io.InvalidObjectException;

public class AddStrategy implements CommandStrategy {

    private CollectionHandler ch = new CollectionHandler();

    public AddStrategy() {}
    public Response execute(UsableCommand cmd) {
        Add add = (Add) cmd;
        Ticket t = add.getTicket();
        t.setCreator(add.getUsername());
        try {
            ch.add(t);
            return new Response(true, "Билет добавлен в коллекцию.");
        } catch (InvalidObjectException e) {
            return new Response(false, e.getMessage());
        }
    }
}
