package shared.command;

import shared.CollectionHandler;

/**
 * Интерфейс для классов команд.
 *
 * @author AstroSoup
 */
public interface Command {
    /**
     * Запускает цикл выполнения конкретной команды.
     */
    void execute();
}
