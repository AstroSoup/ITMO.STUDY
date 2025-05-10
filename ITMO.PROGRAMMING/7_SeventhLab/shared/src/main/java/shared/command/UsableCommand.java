package shared.command;

import jakarta.xml.bind.annotation.XmlElement;

import java.io.Serializable;

/**
 * Абстрактный класс от которого наследуются все команды, которые может вызвать пользователь.
 *
 * @author AstroSoup
 */
public abstract class UsableCommand extends Command implements Serializable {
    final private String description;
    private String name;
    private String username = "";
    private String password = "";
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
    @XmlElement
    public String getUsername() {
        return username;
    }
    @XmlElement
    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
