package utility;

import entity.TicketType;

import java.util.Comparator;

/**
 * Компаратор для TicketType реализующий сравнение для null-значений.
 *
 * @author AstroSoup
 */
public class TicketTypeComparator implements Comparator<TicketType> {
    /**
     * Метод сравнивающий объекты TicketType и null
     * @param t1 первый объект для сравнения
     * @param t2 второй объект для сравнения
     * @return результат сравнения
     */
    public int compare(TicketType t1, TicketType t2) {
        if (t1 == null && t2 == null) return 0;
        if (t2 == null) return 1;
        if (t1 == null) return -1;
        return t2.compareTo(t1);
    }
}
