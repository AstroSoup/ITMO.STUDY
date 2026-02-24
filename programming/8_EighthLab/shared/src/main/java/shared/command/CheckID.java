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
     */
    public CheckID(int id) {
        super("Check_ID", "");
        this.id = id;

    }
    public CheckID() {
        this(-1);
    }



    @XmlElement
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
