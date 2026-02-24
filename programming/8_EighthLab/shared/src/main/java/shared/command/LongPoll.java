package shared.command;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LongPoll extends UsableCommand implements RemoteCommand {

    @XmlElement()
    private long version;

    public LongPoll() {
        super("LongPoll", "");
    }
    public LongPoll(long version) {
        super("LongPoll", "");
        this.version = version;
    }
    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }
}
