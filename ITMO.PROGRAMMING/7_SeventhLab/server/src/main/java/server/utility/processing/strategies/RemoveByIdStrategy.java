package server.utility.processing.strategies;

import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.RemoveById;
import shared.command.UsableCommand;
import shared.entity.Ticket;

public class RemoveByIdStrategy implements CommandStrategy {
    public RemoveByIdStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        RemoveById removeById = (RemoveById) cmd;
        CollectionHandler ch = new CollectionHandler();
        for (Ticket ticket : ch.getCollection()) {
            if (ticket.getId() == removeById.getId()) {
                Response rsp;
                try {
                    ch.remove(ticket.getId());
                    rsp = new Response(true, "Билет убран из коллекции.");
                }catch (Exception e) {
                    rsp = new Response(false, e.getMessage());
                }
                return rsp;
            }
        }
        return new Response(true, "Билет с данным ID отсутствует в коллекции.");
    }
}
