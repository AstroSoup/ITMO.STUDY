package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.CommandHandler;

/**
 * Класс команды для очистки коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Clear extends UsableCommand implements RemoteCommand {
    /**
     * Конструктор
     *
     */
    public Clear() {
        super("Clear", "Команда для очистки коллекции");

    }
}
