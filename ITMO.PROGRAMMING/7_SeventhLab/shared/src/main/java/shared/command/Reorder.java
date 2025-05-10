package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.*;
import shared.CommandHandler;

import java.util.stream.Collectors;

/**
 * Класс команды для вывода всех значений элементов коллекции упорядоченных по убыванию.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Reorder extends UsableCommand implements RemoteCommand {


    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Reorder(CommandHandler invoker) {
        super("Reorder", "Команда для вывода всех значений элементов коллекции упорядоченных по убыванию");
        this.setInvoker(invoker);
    }

    public Reorder() {
        this(null);
    }
}
