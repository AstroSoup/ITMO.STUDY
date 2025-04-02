package client.utility;

import client.clientsideCommand.LocalCommand;
import shared.CommandHandler;
import shared.command.Command;
import shared.command.RemoteCommand;
import shared.exceptions.ConnectionLostException;

import java.io.IOException;

public class ClientCommandHandler extends CommandHandler {

    private NetworkHandler networkHandler;

    public ClientCommandHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    public void invoke(Command cmd) throws ConnectionLostException{
        if (cmd instanceof RemoteCommand) {

            try {
                networkHandler.communicate(this, (RemoteCommand) cmd);
            } catch (IOException e) {
                //e.printStackTrace();
                this.setFeedback("Отсутствует связь с сервером. Команда не была исполнена.");
                throw new ConnectionLostException();
            }
        } else {
            cmd.execute();
        }

    }
}
