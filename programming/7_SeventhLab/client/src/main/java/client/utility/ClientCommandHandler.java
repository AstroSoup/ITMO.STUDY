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


    public ClientCommandHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    public void invoke(Command cmd) throws ConnectionLostException{

        if (cmd instanceof RemoteCommand) {
            if (cmd instanceof UsableCommand && !(cmd instanceof LoginCommand)) {
                ((UsableCommand) cmd).setUsername(username);
                ((UsableCommand) cmd).setPassword(password);
            }
            try {
                networkHandler.communicate(this, (RemoteCommand) cmd);
            } catch (IOException e) {
                //e.printStackTrace();
                this.setFeedback("Отсутствует связь с сервером. Команда не была исполнена.");
                throw new ConnectionLostException();
            }

            if (cmd instanceof LoginCommand) {

                if (this.getFeedback().trim().equals("Вы успешно вошли в аккаунт.")  || this.getFeedback().trim().equals("Вы успешно зарегитрировались.")) {
                    //System.out.println(".");
                    username = ((LoginCommand) cmd).getUsername();
                    password = ((LoginCommand) cmd).getPassword();
                }
            }
        }

    }
    public void invoke(LocalCommand cmd) {
        cmd.execute();
    }
}
