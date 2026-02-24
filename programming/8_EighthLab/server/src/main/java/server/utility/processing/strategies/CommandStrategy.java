package server.utility.processing.strategies;

import server.utility.response.Response;
import shared.command.UsableCommand;

public interface CommandStrategy {
    Response execute(UsableCommand cmd);
}
