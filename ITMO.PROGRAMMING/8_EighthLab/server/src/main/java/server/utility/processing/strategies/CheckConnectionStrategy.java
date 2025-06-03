package server.utility.processing.strategies;

import server.utility.response.Response;
import shared.command.UsableCommand;

public class CheckConnectionStrategy implements CommandStrategy{
    public CheckConnectionStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        return new Response(true, "1");
    }
}
