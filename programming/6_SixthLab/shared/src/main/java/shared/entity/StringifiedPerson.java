package shared.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringifiedPerson {
    private String passportID;
    private String eyeColor;
    private StringifiedLocation location;

    public StringifiedPerson(String passportID, String eyeColor, StringifiedLocation location) {
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.location = location;
    }
    public StringifiedPerson() {
    }
    @XmlElement
    public String getPassportID() {
        return passportID;
    }
    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }
    @XmlElement
    public String getEyeColor() {
        return eyeColor;
    }
    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }
    @XmlElement
    public StringifiedLocation getLocation() {
        return location;
    }
    public void setLocation(StringifiedLocation location) {
        this.location = location;
    }


}
