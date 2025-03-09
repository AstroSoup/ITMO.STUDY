package utility;

import entity.*;
import exceptions.InvalidElementException;
import exceptions.NotEnoughRightsToWriteException;

import java.io.File;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Comparator;
import java.util.Vector;
/**
 * Класс, управляющий коллекцией. Реализует паттерн синглтон, т.к. хранит коллекцию.
 *
 * @author AstroSoup
 */
public class CollectionHandler {

    private Vector<Ticket> collection = new Vector<>();

    private static String creationDT;
    private static CollectionHandler INSTANCE = null;

    private CollectionHandler(){
    }

    /**
     * Метод возвращает единственный экземпляр класса или создает новый.
     *
     * @return единственный экземпляр класса CollectionHandler.
     */
    public static CollectionHandler getHandler() {
        if (INSTANCE == null) {
            INSTANCE = new CollectionHandler();
            String[] timeStringArr = java.time.LocalDateTime.now().toString().split("T");
            creationDT = timeStringArr[0] + " " + timeStringArr[1].substring(0,timeStringArr[1].indexOf("."));

        }
        return INSTANCE;
    }

    /**
     * Геттер для времени создания коллекции.
     * @return Время создания коллекции в строковом представлении
     */
    public String getCDT() {
        return creationDT;
    }
    /**
     * Сеттер для времени создания коллекции.
     * @param creationDT дата и время создания коллекции
     */
    public void setCDT(String creationDT) {
        CollectionHandler.creationDT = creationDT;
    }
    /**
     * Геттер для коллекции.
     * @return Вектор значений сохраненных в коллекции
     */
    public Vector<Ticket> getCollection() {
        return collection;
    }

    /**
     * Сеттер для коллекции.
     * @param collection Вектор элементов Ticket
     */
    public void setCollection(Vector<Ticket> collection) throws InvalidObjectException {
        for (Ticket t : collection) {
            if (!t.validate()) throw new InvalidObjectException("Ошибка в параметрах билета");
        }
        this.collection = collection;
    }

    /**
     * Метод для добавления новых элементов в коллекцию.
     * @param ticket Объект класса Ticket
     */
    public void add(Ticket ticket) throws InvalidObjectException {
        if (!ticket.validate()) throw new InvalidObjectException("Ошибка в параметрах билета");
        collection.add(ticket);
    }

    /**
     * Метод очищающий коллекцию.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Метод для вставки элемента в коллекцию по индексу.
     * @param index индекс по которому нужно вставить элемент
     * @param ticket Объект класса Ticket
     */
    public void insert(int index, Ticket ticket) throws InvalidObjectException {
        if (!ticket.validate()) throw new InvalidObjectException("Ошибка в параметрах билета");
        collection.add(index, ticket);
    }

    /**
     * Метод для удаления элемента из коллекции.
     * @param ticket Объект класса Ticket
     */
    public void remove(Ticket ticket) {
        collection.remove(ticket);
    }

    /**
     * Метод сортирующий коллекцию по убыванию цены билета.
     */
    public void reorder() {
        collection.sort(Comparator.reverseOrder());
    }

    /**
     * Метод сортирующий коллекцию по возрастанию цены билета.
     */
    public void sort() {
        collection.sort(Comparator.naturalOrder());
    }
    public void save() throws NotEnoughRightsToWriteException, IOException {
        String path = System.getenv("FIFTH_LAB_SAVING_FILE_PATH");
        File file = new File(path);
        XMLWriter writer = new XMLWriter(file);
        if (!file.canWrite()) throw new NotEnoughRightsToWriteException();
        writer.write(new TicketVectorWrapper(CollectionHandler.getHandler().getCollection(),CollectionHandler.getHandler().getCDT()));
    }
}

