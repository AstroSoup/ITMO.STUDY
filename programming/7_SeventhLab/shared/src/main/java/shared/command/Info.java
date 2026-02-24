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
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Info(CommandHandler invoker) {
        super("Info", "Команда для получения мета-информации о коллекции");
        this.setInvoker(invoker);
    }

    public Info() {
        this(null);
    }
}
