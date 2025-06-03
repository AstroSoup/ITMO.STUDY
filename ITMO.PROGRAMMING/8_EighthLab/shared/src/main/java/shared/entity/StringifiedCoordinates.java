package shared.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringifiedCoordinates {
    private String x;
    private String y;
    public StringifiedCoordinates() {
    }
    public StringifiedCoordinates(String x, String y) {
        this.x = x;
        this.y = y;
    }
    @XmlElement
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }
    @XmlElement
    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }
}
