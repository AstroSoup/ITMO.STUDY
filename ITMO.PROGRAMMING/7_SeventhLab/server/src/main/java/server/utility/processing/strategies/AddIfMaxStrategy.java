package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.AddIfMax;
import shared.command.UsableCommand;

import java.io.InvalidObjectException;
import java.util.Comparator;

public class AddIfMaxStrategy implements CommandStrategy{
    public AddIfMaxStrategy() {}

    public Response execute(UsableCommand cmd) {
        CollectionHandler ch = new CollectionHandler();
        AddIfMax addIfMax = (AddIfMax)cmd;

        if (addIfMax.getTicket().compareTo(
                ch.getCollection().stream().max(Comparator.naturalOrder()).get()) == 1) {
            try {
                ch.add(addIfMax.getTicket());
                return new Response(true, "Билет добавлен в коллекцию.");
            } catch (InvalidObjectException e) {
                return new Response(false, e.getMessage());
            }
        } else {
            return new Response(true, "Билет не является максимальным.");
        }
    }
}
