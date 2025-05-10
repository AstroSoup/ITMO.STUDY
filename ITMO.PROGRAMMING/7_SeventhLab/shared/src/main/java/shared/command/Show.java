package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CommandHandler;

import java.util.stream.Collectors;

/**
 * Класс команды для вывода всех элементов коллекции упорядоченных по возрастанию.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Show extends UsableCommand implements RemoteCommand {



    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Show(CommandHandler invoker) {
        super("Show", "Команда для вывода всех элементов коллекции упорядоченных по возрастанию");
        this.setInvoker(invoker);
    }

    public Show() {
        this(null);
    }
}
