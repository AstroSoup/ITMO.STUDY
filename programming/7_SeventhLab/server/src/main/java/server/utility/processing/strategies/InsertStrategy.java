package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.Insert;
import shared.command.UsableCommand;

import java.io.InvalidObjectException;

public class InsertStrategy implements CommandStrategy {
    public InsertStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        Insert insert = (Insert) cmd;
        try {
            new CollectionHandler().insert(insert.getIndex(), insert.getTicket());
            return new Response(true, "Билет добавлен в коллекцию по индексу " + insert.getIndex() + ".");
        } catch (InvalidObjectException e) {
            return new Response(false, e.getMessage());
        }
    }
}
