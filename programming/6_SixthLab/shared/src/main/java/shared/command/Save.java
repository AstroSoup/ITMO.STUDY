package shared.command;


import jakarta.xml.bind.annotation.XmlRootElement;
import shared.exceptions.NotEnoughRightsToWriteException;
import shared.CollectionHandler;
import shared.CommandHandler;

import java.io.IOException;
/**
 * Класс команды для сохранения коллекции в файл.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Save extends UsableCommand implements RemoteCommand {

    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;
    /**
     * Конструктор
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Save(CommandHandler invoker) {
        super("Save", "Команда для сохранения коллекции в файл");
        this.invoker = invoker;
    }
    public Save() {
        this(null);
    }

    public void execute() {
        try {
            collectionHandler.save();
            invoker.setFeedback("Коллекция сохранена.");
        } catch (IOException e) {
            invoker.setFeedback("Файл сохранения не найден. Коллекция не была сохранена.");
        } catch (NotEnoughRightsToWriteException e) {
            invoker.setFeedback(e.getMessage());
        }
    }
}
