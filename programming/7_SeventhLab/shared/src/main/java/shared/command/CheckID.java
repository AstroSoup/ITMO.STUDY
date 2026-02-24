package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.Ticket;
import shared.CommandHandler;

/**
 * Класс команды для проверки наличия элемента с некоторым уникальным идентификатором в коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class CheckID extends UsableCommand implements RemoteCommand {

    private CommandHandler invoker;
    private int id;

    /**
     * Конструктор
     *
     * @param id      Уникальный идентификатор, наличие которого проверяет команда
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public CheckID(int id, CommandHandler invoker) {
        super("Check_ID", "");
        this.id = id;
        this.setInvoker(invoker);
    }
    public CheckID() {
        super("Check_ID", "");
        this.id = -1;
        this.invoker = null;
    }



    @XmlElement
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
