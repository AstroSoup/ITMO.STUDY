package shared.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/**
 * Класс, задающий точку прибытия в билете.
 *
 * @author AstroSoup
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates implements Validatable, Serializable {
    @XmlElement
    private Float x; //Максимальное значение поля: 952, Поле не может быть null
    @XmlElement
    private Double y; //Поле не может быть null

    /**
     * Конструктор
     *
     * @param x координата X(не может быть null, должна быть меньше 952)
     * @param y координата Y(не может быть null)
     */
    public Coordinates(Float x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    
    public Float getX() {
        return x;
    }

    
    public Double getY() {
        return y;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    /**
     * Проверка валидности созданного объекта.
     *
     * @return true - если объект создан правильно, false в ином случае
     */
    @Override
    public boolean validate() {
        return validateX(this.x) && validateY(this.y);
    }

    public static boolean validateX(Float x) {
        return x != null && x < 952;
    }
    public static boolean validateY(Double y) {
        return y != null;
    }

    @Override
    public String toString() {
        return "x:" + x +
                ", y:" + y;
    }
}
