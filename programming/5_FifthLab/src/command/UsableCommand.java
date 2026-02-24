package command;

/**
 * Абстрактный класс от которого наследуются все команды, которые может вызвать пользователь.
 *
 * @author AstroSoup
 */
public abstract class UsableCommand implements Command {
    final private String description;
    final private String name;

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

    public String getName() {
        return name;
    }
}
