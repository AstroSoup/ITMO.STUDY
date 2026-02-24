package command;

/**
 * Класс команды для закрытия приложения.
 *
 * @author AstroSoup
 */
public class Exit extends UsableCommand {


    public Exit() {
        super("Exit", "Команда для закрытия приложения");
    }

    public void execute() {
        System.exit(0);
    }
}
