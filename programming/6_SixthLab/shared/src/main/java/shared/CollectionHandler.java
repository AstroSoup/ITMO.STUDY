package shared;


import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Vector;

import shared.entity.*;

public interface CollectionHandler {




    /**
     * Геттер для времени создания коллекции.
     *
     * @return Время создания коллекции в строковом представлении
     */
    public String getCDT();

    /**
     * Сеттер для времени создания коллекции.
     *
     * @param creationDT дата и время создания коллекции
     */
    public void setCDT(String creationDT);

    /**
     * Геттер для коллекции.
     *
     * @return Вектор значений сохраненных в коллекции
     */
    public Vector<Ticket> getCollection();

    /**
     * Сеттер для коллекции.
     *
     * @param collection Вектор элементов Ticket
     */
    public void setCollection(Vector<Ticket> collection) throws InvalidObjectException;

    /**
     * Метод для добавления новых элементов в коллекцию.
     *
     * @param ticket Объект класса Ticket
     */
    public void add(Ticket ticket) throws InvalidObjectException;

    /**
     * Метод очищающий коллекцию.
     */
    public void clear();

    /**
     * Метод для вставки элемента в коллекцию по индексу.
     *
     * @param index  индекс по которому нужно вставить элемент
     * @param ticket Объект класса Ticket
     */
    public void insert(int index, Ticket ticket) throws InvalidObjectException;

    /**
     * Метод для удаления элемента из коллекции.
     *
     * @param ticket Объект класса Ticket
     */
    public void remove(Ticket ticket);

    /**
     * Метод сортирующий коллекцию по убыванию цены билета.
     */
    public void reorder();

    /**
     * Метод сортирующий коллекцию по возрастанию цены билета.
     */
    public void sort();

    public void save() throws shared.exceptions.NotEnoughRightsToWriteException, IOException;
}
