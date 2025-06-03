package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CommandHandler;

import java.util.Comparator;
import java.util.Vector;

/**
 * Класс команды для вывода мета-информации о коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Info extends UsableCommand implements RemoteCommand {


    /**
     * Конструктор
     *
     */
    public Info() {
        super("Info", "Команда для получения мета-информации о коллекции");

    }
}
