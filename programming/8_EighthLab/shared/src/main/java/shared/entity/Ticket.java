package shared.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import shared.utility.LocalDateAdapter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import java.io.Serializable;

/**
 * Класс, задающий объекты билета. Экземпляры этого класса хранятся в коллекции.
 *
 * @author AstroSoup
 */
@XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
public class Ticket implements Comparable<Ticket>, Validatable, Serializable {
    @XmlElement
    private Integer id; //+Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement
    private Coordinates coordinates; //Поле не может быть null
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate creationDate; //+Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XmlElement
    private Long price; //Поле не может быть null, Значение поля должно быть больше 0
    @XmlElement
    private float discount; //Значение поля должно быть больше 0, Максимальное значение поля: 100
    @XmlElement
    private TicketType type; //Поле может быть null
    @XmlElement
    private Person person; //Поле не может быть null
    @XmlElement
    private String creator = "";




    public Ticket(String name,
                  Long price,
                  float discount,
                  TicketType type,
                  Coordinates coordinates,
                  Person person,
                  String creator) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.person = person;
        this.creationDate = java.time.LocalDate.now();
        this.id = UUID.randomUUID().hashCode();
        this.creator = creator;
    }

    /**
     * Конструктор
     *
     * @param name        Имя пассажира(Строка, не может быть null)
     * @param price       цена билета(целочисленное положительное значение, не может быть null)
     * @param discount    скидка на билет(число с плавающей точкой от 0 до 100)
     * @param type        тип вагона указанный в билете(TicketType, может быть null)
     * @param coordinates координаты точки прибытия(Coordinates, не может быть null)
     * @param person      пассажир(Person, не может быть null)
     */
    public Ticket(String name,
                  Long price,
                  float discount,
                  TicketType type,
                  Coordinates coordinates,
                  Person person) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.person = person;
        this.creationDate = java.time.LocalDate.now();
        this.id = UUID.randomUUID().hashCode();
    }

    public Ticket() {
    }

    /**
     * Метод для сравнения объектов Ticket. Сравнение происходит по стоимости билета.
     *
     * @param o Объект для сравнения
     * @return Цена объекта - цена о
     */
    public int compareTo(Ticket o) {
        return (int) (this.price - o.price);
    }

    public TicketType getType() {
        return type;
    }


    public Long getPrice() {
        return price;
    }


    public String getName() {
        return name;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }


    public float getDiscount() {
        return discount;
    }


    public Person getPerson() {
        return person;
    }


    public Integer getId() {
        return id;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreator(String creator) {this.creator = creator;}

    @Override
    public boolean validate() {
        return  validateName(this.name)
                && person != null && person.validate()
                && coordinates != null && coordinates.validate()
                && creationDate != null
                && validatePrice(this.price)
                && validateDiscount(this.discount);
    }
    public static boolean validateName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() < 1000;
    }
    public static boolean validatePrice(Long price) {
        return price != null && price > 0;
    }
    public static boolean validateDiscount(float discount) {
        return discount <=100 && discount > 0;
    }

    @Override
    public String toString() {
        return "Билет №" + id +
                ", имя пассажира: '" + name + '\'' +
                ", координаты точки прибытия: " + coordinates +
                ", дата покупки: " + creationDate.toString() +
                ", цена: " + price +
                ", скидка: " + discount + "%" +
                ", Вагон типа: " + (type == null ? "На коленочках у проводника" : type.toString()) +
                ", " + person.toString() +
                ", Билет добавил: " + creator;
    }
}
