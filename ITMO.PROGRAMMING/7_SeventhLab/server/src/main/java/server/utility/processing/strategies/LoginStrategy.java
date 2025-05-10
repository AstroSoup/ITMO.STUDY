package server.utility.processing.strategies;

import server.auth.AuthHandler;
import server.utility.response.Response;
import shared.command.Command;
import shared.command.Login;
import shared.command.UsableCommand;

public class LoginStrategy implements CommandStrategy {
    public LoginStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        AuthHandler ah = new AuthHandler();
        Login login = (Login) cmd;
        if (ah.login(login.getUsername(), login.getPassword())) return new Response(true, "Вы успешно вошли в аккаунт.");
        else return new Response(true, "Вы указали неверное имя пользователя или пароль.");
    }
}
