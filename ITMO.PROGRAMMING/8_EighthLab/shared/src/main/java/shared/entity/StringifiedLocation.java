package shared.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringifiedLocation {
    private String x;
    private String y;
    private String z;
    public StringifiedLocation() {
    }
    public StringifiedLocation(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    @XmlElement
    public String getZ() {
        return z;
    }
    public void setZ(String z) {
        this.z = z;
    }
}
