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
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Clear(CommandHandler invoker) {
        super("Clear", "Команда для очистки коллекции");
        this.setInvoker(invoker);
    }

    public Clear() {
        this(null);
    }
}
