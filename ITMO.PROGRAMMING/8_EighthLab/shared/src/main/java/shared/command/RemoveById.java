package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.*;
import shared.CommandHandler;

/**
 * Класс команды для удаления элемента из коллекции по его уникальному идентификатору.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class RemoveById extends UsableCommand implements RemoteCommand {

    private int id;

    /**
     * Конструктор
     *
     * @param id      Уникальный идентификатор элемента который необходимо удалить из коллекции
     */
    public RemoveById(int id) {
        super("Remove_by_id", "Команда для удаления элемента из коллекции по его уникальному идентификатору. Уникальный идентификатор необходимо указать на той же строке, что и имя команды");
        this.id = id;

    }

    public RemoveById() {
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
