package client.utility;

import client.clientsideCommand.LocalCommand;
import shared.CommandHandler;
import shared.command.*;
import shared.exceptions.ConnectionLostException;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;

public class ClientCommandHandler extends CommandHandler {

    private NetworkHandler networkHandler;
    private String username = "";
    private String password = "";


    public ClientCommandHandler() {
        this.networkHandler = null;
    }


    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    public void invoke(Command cmd) throws ConnectionLostException{

        if (cmd instanceof RemoteCommand) {
            if (cmd instanceof UsableCommand && !(cmd instanceof LoginCommand)) {
                ((UsableCommand) cmd).setUsername(username);
                ((UsableCommand) cmd).setPassword(password);
            }
            try {
                networkHandler.sendCommand((RemoteCommand) cmd);
                this.setFeedback(networkHandler.receiveResponse());
            } catch (IOException e) {

                this.setFeedback("Отсутствует связь с сервером. Команда не была исполнена.");
                throw new ConnectionLostException();
            }

            if (this.getFeedback().trim().equals("Вы успешно вошли в аккаунт.")  || this.getFeedback().trim().equals("Вы успешно зарегитрировались.")) {
                username = ((LoginCommand) cmd).getUsername();
                password = ((LoginCommand) cmd).getPassword();
            }



        }

    }
    public void invoke(LocalCommand cmd) {
        cmd.execute();
    }
}
