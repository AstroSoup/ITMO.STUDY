package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.CommandHandler;
@XmlRootElement
public class CheckConnection extends UsableCommand implements RemoteCommand {


    private String dummy = "SOMEDATA";

    public CheckConnection() {
        super("Check_Connection", "");

    }

    @XmlElement
    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
    public String getDummy() {
        return dummy;
    }
}
