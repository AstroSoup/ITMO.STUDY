package shared.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringifiedTicket {
    private String name;
    private String price;
    private String discount;
    private String type;
    private StringifiedCoordinates coordinates;
    private StringifiedPerson person;

    public StringifiedTicket() {
    }
    public StringifiedTicket(String name, String price, String discount, String type, StringifiedCoordinates coordinates, StringifiedPerson person) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.coordinates = coordinates;
        this.person = person;
    }
    @XmlElement
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @XmlElement
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    @XmlElement
    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    @XmlElement
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @XmlElement
    public StringifiedCoordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(StringifiedCoordinates coordinates) {
        this.coordinates = coordinates;
    }
    @XmlElement
    public StringifiedPerson getPerson() {
        return person;
    }
    public void setPerson(StringifiedPerson person) {
        this.person = person;
    }
}
