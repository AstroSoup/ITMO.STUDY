package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.CommandHandler;

/**
 * Класс команды для проверки размера коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class CheckSize extends UsableCommand implements RemoteCommand {
    private CommandHandler invoker;
    private int index;
    /**
     * Конструктор
     *
     * @param index   индекс для которого осуществляется проверка размера коллекции
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public CheckSize(int index, CommandHandler invoker) {
        super("Check_Size", "");
        this.index = index;
        this.setInvoker(invoker);
    }
    public CheckSize() {
        super("Check_Size", "");
        this.index = -1;
        this.invoker = null;
    }


    @XmlElement
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
