package shared.command;

import jakarta.xml.bind.annotation.XmlRootElement;
import shared.entity.*;
import shared.CommandHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

/**
 * Класс команды для вывода всех значений полей TicketType элементов коллекции упорядоченных по убыванию.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class PrintFieldDescendingType extends UsableCommand implements RemoteCommand {

    /**
     * Конструктор
     *
     */
    public PrintFieldDescendingType() {
        super("Print_field_descending_type", "Команда для вывода всех значений полей TicketType элементов коллекции упорядоченных по убыванию");

    }
}