package shared.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/**
 * Класс, задающий пассажира.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Person implements Validatable, Serializable {
    private String passportID; //Строка не может быть пустой, Длина строки должна быть не меньше 6, Длина строки не должна быть больше 28, Поле не может быть null
    private Color eyeColor; //Поле может быть null
    private Location location; //Поле может быть null

    /**
     * Конструктор
     *
     * @param passportID Паспортные данные(строка от 6 до 28 символов, не может быть null)
     * @param eyeColor   Цвет глаз(Объект enum'а Color, не может быть null)
     * @param location   Координаты точки отправления(не может быть null)
     */
    public Person(String passportID, Color eyeColor, Location location) {
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.location = location;
    }

    public Person() {
    }

    @XmlElement
    public String getPassportID() {
        return passportID;
    }

    @XmlElement
    public Location getLocation() {
        return location;
    }

    @XmlElement
    public Color getEyeColor() {
        return eyeColor;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    /**
     * Проверка валидности созданного объекта.
     *
     * @return true - если объект создан правильно, false в ином случае
     */
    @Override
    public boolean validate() {
        return validateEyeColor(this.eyeColor) && location != null && location.validate();
    }
    public static boolean validateEyeColor(Color eyeColor) {
        return eyeColor != null;
    }
    public static boolean validatePassportID(String passportID) {
        return passportID != null && passportID.length() >= 6 && passportID.length() <= 28;
    }


    @Override
    public String toString() {
        return "паспортные данные пассажира: " + passportID +
                ", цвет глаз пассажира: " + eyeColor +
                ", координаты точки отбытия: " + location.toString();
    }
}
