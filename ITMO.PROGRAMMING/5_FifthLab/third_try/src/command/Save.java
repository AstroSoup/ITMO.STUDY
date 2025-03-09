package command;

import entity.TicketVectorWrapper;
import exceptions.NotEnoughRightsToWriteException;
import utility.CollectionHandler;
import utility.CommandHandler;
import utility.XMLWriter;

import java.io.File;
import java.io.IOException;
/**
 * Класс команды для сохранения коллекции в файл.
 *
 * @author AstroSoup
 */
public class Save extends UsableCommand {

    final private CommandHandler invoker;
    /**
     * Конструктор
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Save(CommandHandler invoker) {
        super("Save", "Команда для сохранения коллекции в файл");
        this.invoker = invoker;
    }

    public void execute() {
        try {
            CollectionHandler.getHandler().save();
            invoker.setFeedback("Коллекция сохранена.");
        } catch (IOException e) {
            invoker.setFeedback("Файл сохранения не найден. Коллекция не была сохранена.");
        } catch (NotEnoughRightsToWriteException e) {
            invoker.setFeedback(e.getMessage());
        }
    }
}
