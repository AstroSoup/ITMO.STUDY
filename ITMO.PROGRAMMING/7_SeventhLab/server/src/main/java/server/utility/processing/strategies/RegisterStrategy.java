package server.utility.processing.strategies;

import server.auth.AuthHandler;
import server.utility.response.Response;
import shared.command.Register;
import shared.command.UsableCommand;

public class RegisterStrategy implements CommandStrategy {
    public RegisterStrategy() {}

    @Override
    public Response execute(UsableCommand cmd) {
        AuthHandler ah = new AuthHandler();
        Register reg = (Register) cmd;
        if (ah.register(reg.getUsername(), reg.getPassword())) return new Response(true, "Вы успешно зарегитрировались.");
        else return new Response(true, "Пользователь с таким именем уже зарегестрирован в системе.");
    }
}
