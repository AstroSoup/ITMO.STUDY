package shared.command;

import jakarta.xml.bind.annotation.XmlElement;

import java.io.Serializable;

/**
 * Абстрактный класс от которого наследуются все команды, которые может вызвать пользователь.
 *
 * @author AstroSoup
 */
public abstract class UsableCommand implements Command, Serializable {
    final private String description;
    private String name;

    /**
     * Конструктор
     *
     * @param name        имя команды
     * @param description описание работы команды и входных данных
     */
    public UsableCommand(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
