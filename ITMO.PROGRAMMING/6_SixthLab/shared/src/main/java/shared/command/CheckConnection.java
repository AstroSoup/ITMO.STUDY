package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.CollectionHandler;
import shared.CommandHandler;
@XmlRootElement
public class CheckConnection implements Command, RemoteCommand {

    private CommandHandler invoker;
    CollectionHandler collectionHandler = null;
    private String dummy = "SOMEDATA";

    public CheckConnection(CommandHandler invoker) {
        this.invoker = invoker;
    }

    public CheckConnection() {
        this.invoker = null;
    }

    public void execute(){
        invoker.setFeedback("1");
    }
    @XmlElement
    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
    public String getDummy() {
        return dummy;
    }
}
