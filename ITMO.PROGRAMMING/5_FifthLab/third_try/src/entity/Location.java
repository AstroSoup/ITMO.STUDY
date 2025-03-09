package entity;


import jakarta.xml.bind.Element;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

/**
 * Класс, задающий точку отбытия в билете.
 *
 * @author AstroSoup
 */
@XmlRootElement
public class Location implements Validatable, Serializable {
    private long x;
    private Long y; //Поле не может быть null
    private long z;
     /**
     * Конструктор
     * @param x координата X
     * @param y координата Y(не может быть null)
     * @param z координата Z
     */
    public Location(long x, Long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Location() {}
    @XmlElement
    public long getX() {
        return x;
    }
    @XmlElement
    public Long getY() {
        return y;
    }
    @XmlElement
    public long getZ() {
        return z;
    }
    public void setX(long x) {
        this.x = x;
    }
    public void setY(Long y) {
        this.y = y;
    }
    public void setZ(long z) {
        this.z = z;
    }
    /**
     * Проверка валидности созданного объекта.
     * @return true - если объект создан правильно, false в ином случае
     */
    @Override
    public boolean validate() {
        return y != null;
    }

    @Override
    public String toString() {
        return "x: " + x +
                ", y: " + y +
                ", z: " + z;
    }
}
