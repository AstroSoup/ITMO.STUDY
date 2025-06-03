package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.*;
import shared.CommandHandler;

import java.util.Objects;
import java.util.Vector;

/**
 * Класс команды для обновления данных элемента по его уникальному идентификатору.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Update extends UsableCommand implements RemoteCommand {

    private StringifiedTicket ticket;
    private int id;


    /**
     * Конструктор
     *
     * @param ticket  Элемент
     * @param id      индекс в коллекции по которому нужно вставить элемент
     */
    public Update(int id, StringifiedTicket ticket) {
        super("Update", "Команда для обновления данных элемента по его уникальному идентификатору. Уникальный идентификатор необходимо указать на той же строке, что и имя команды. Каждое поле добавляемого элемента вводится на отдельной строке");
        this.id = id;
        this.ticket = ticket;

    }

    public Update() {
        this(-1, null);
    }

    @XmlElement
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public StringifiedTicket getTicket() {
        return ticket;
    }
    public void setTicket(StringifiedTicket ticket) {
        this.ticket = ticket;
    }
}
