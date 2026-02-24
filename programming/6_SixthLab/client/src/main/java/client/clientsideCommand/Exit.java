package client.clientsideCommand;

import shared.CollectionHandler;
import shared.command.UsableCommand;

/**
 * Класс команды для закрытия приложения.
 *
 * @author AstroSoup
 */
public class Exit extends UsableCommand implements LocalCommand {


    public Exit() {
        super("Exit", "Команда для закрытия приложения");
    }

    public void execute(CollectionHandler ch) {
        this.execute();
    }

    public void execute() {
        System.exit(0);
    }
}
