package server.utility;

import server.storage.DBHandler;
import shared.entity.*;

import java.io.InvalidObjectException;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Класс, управляющий коллекцией. Реализует паттерн синглтон, т.к. хранит коллекцию.
 *
 * @author AstroSoup
 */
public class CollectionHandler {

    private Vector<Ticket> collection = new Vector<>();
    private static final Logger COLLECTIONLOGGER = Logger.getLogger("server.utility.CollectionHandler");
    public static boolean hasNewData = false;
    public CollectionHandler() {
        try {
            this.setCollection(new DBHandler().readAllTickets());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Геттер для коллекции.
     *
     * @return Вектор значений сохраненных в коллекции
     */
    public Vector<Ticket> getCollection() {
        return collection;
    }

    /**
     * Сеттер для коллекции.
     *
     * @param collection Вектор элементов Ticket
     */
    public void setCollection(Vector<Ticket> collection) throws InvalidObjectException {
        for (Ticket t : collection) {
            if (!t.validate()) throw new InvalidObjectException("Ошибка в параметрах билета");
        }
        this.collection = collection;
        hasNewData = true;
    }

    /**
     * Метод для добавления новых элементов в коллекцию.
     *
     * @param ticket Объект класса Ticket
     */
    public void add(Ticket ticket) throws InvalidObjectException {
        if (!ticket.validate()) throw new InvalidObjectException("Ошибка в параметрах билета");
        if (new DBHandler().write(ticket)) COLLECTIONLOGGER.fine("В коллекцию добавлен новый билет.");
        hasNewData = true;
    }

    /**
     * Метод очищающий коллекцию.
     */
    public void clear() {
        if (new DBHandler().removeAll()) COLLECTIONLOGGER.fine("Коллекция очищена.");
        hasNewData = true;
    }

    /**
     * Метод для вставки элемента в коллекцию по индексу.
     *
     * @param index  индекс по которому нужно вставить элемент
     * @param ticket Объект класса Ticket
     */
    public void insert(int index, Ticket ticket) throws InvalidObjectException {
        if (!ticket.validate()) throw new InvalidObjectException("Ошибка в параметрах билета");
        this.add(ticket);
    }

    /**
     * Метод для удаления элемента из коллекции.
     *
     * @param id id билета для удаления из коллекции
     */
    public void remove(int id) throws InvalidObjectException {
        if (new DBHandler().remove(id)) COLLECTIONLOGGER.fine("Из коллекции удален билет № " + id);
        hasNewData = true;
    }

    /**
     * Метод сортирующий коллекцию по убыванию цены билета.
     */
    public void reorder() {
        collection.sort(Comparator.reverseOrder());
        COLLECTIONLOGGER.fine("Коллекция отсортирована по убыванию");
    }

    /**
     * Метод сортирующий коллекцию по возрастанию цены билета.
     */
    public void sort() {
        collection.sort(Comparator.naturalOrder());
        COLLECTIONLOGGER.fine("Коллекция отсортирована по возрастанию");
    }

    public void update(Ticket ticket) throws InvalidObjectException {
        if (new DBHandler().update(ticket)) COLLECTIONLOGGER.fine("Билет обновлен.");
        hasNewData = true;
    }
}

