package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CommandHandler;

import java.util.Vector;

/**
 * Класс команды для вывода среднего значения поля цены всех элементов коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class AverageOfPrice extends UsableCommand implements RemoteCommand {

    /**
     * Конструктор
     *
     */
    public AverageOfPrice() {
        super("Average_of_price", "Команда для вывода среднего значения поля цены всех элементов коллекции");
    }
}
