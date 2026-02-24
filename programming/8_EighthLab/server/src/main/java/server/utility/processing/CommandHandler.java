package server.utility.processing;

import server.utility.CollectionHandler;
import server.utility.processing.strategies.CommandStrategy;
import server.utility.response.Response;
import shared.command.*;

import java.util.HashMap;
import java.util.function.Function;

public class CommandHandler {

    private static final HashMap<String, Function<Void, CommandStrategy>> strategies = new HashMap<String, Function<Void, CommandStrategy>>();

    private CollectionHandler ch = new CollectionHandler();
    private UsableCommand cmd;
    private CommandStrategy strategy;

    public CommandHandler(UsableCommand cmd) {
        this.cmd = cmd;
        this.strategy = strategies.get(cmd.getName().toUpperCase()).apply(null);
    }
    public static void setStrategies(HashMap<String, Function<Void, CommandStrategy>> strats) {
        strategies.putAll(strats);
    }
    public void setCommandStrategy(CommandStrategy strategy) {
        this.strategy = strategy;
    }
    public Response invoke() {
        return this.strategy.execute(cmd);
    }

}
