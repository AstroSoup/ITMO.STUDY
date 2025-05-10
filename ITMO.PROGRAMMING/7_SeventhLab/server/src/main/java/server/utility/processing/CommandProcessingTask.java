package server.utility.processing;

import server.utility.network.SelectionTask;
import server.utility.network.SocketChannelWrapper;
import server.utility.response.Response;
import server.auth.AuthHandler;
import shared.command.Register;
import shared.command.UsableCommand;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class CommandProcessingTask implements Callable<Response> {
    private UsableCommand cmd;
    private static final Logger CPTLOGGER = Logger.getLogger(CommandProcessingTask.class.getName());
    public CommandProcessingTask(UsableCommand cmd) {
        this.cmd = cmd;
    }
    public Response call() {
        CPTLOGGER.info("running cpt");
        CommandHandler ch = new CommandHandler(cmd);
        if (new AuthHandler().login(cmd.getUsername(), cmd.getPassword()) || cmd instanceof Register) return ch.invoke();
        else return new Response(false, "Для выполнения команд, зайдите в аккаунт или зарегестрируйтесь.");
    }
}
