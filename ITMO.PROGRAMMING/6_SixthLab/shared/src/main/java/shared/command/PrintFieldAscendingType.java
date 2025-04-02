package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.*;
import shared.CollectionHandler;
import shared.CommandHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

/**
 * Класс команды для вывода всех значений полей TicketType элементов коллекции упорядоченных по возрастанию.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class PrintFieldAscendingType extends UsableCommand implements RemoteCommand {

    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;
    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */

    public PrintFieldAscendingType(CommandHandler invoker) {
        super("Print_field_ascending_type", "Команда для вывода всех значений полей TicketType элементов коллекции упорядоченных по возрастанию");
        this.invoker = invoker;
    }

    public PrintFieldAscendingType() {
        this(null);
    }

    public void execute() {
        Vector<TicketType> result = new Vector<>();
        Vector<Ticket> collection = collectionHandler.getCollection();

        for (Ticket t : collection) {
            result.add(t.getType());
        }
        result.sort(Comparator.nullsFirst(Comparator.reverseOrder()));
        ArrayList<String> resultStringList = new ArrayList<>();
        for (TicketType t : result) {
            resultStringList.add(t == null ? "На коленочках у проводника" : t.toString());

        }

        invoker.setFeedback(collection.isEmpty() ? "Коллекция пуста." : "Типы вагонов в билетах отсортированные по возрастанию кошерности: " + String.join(", ", resultStringList));
    }
}
