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
    private int index;
    /**
     * Конструктор
     *
     * @param index   индекс для которого осуществляется проверка размера коллекции
     */
    public CheckSize(int index) {
        super("Check_Size", "");
        this.index = index;

    }
    public CheckSize() {
        this(-1);
    }


    @XmlElement
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
