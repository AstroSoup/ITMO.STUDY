package client.utility;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import shared.command.RemoteCommand;
import shared.command.UsableCommand;
import shared.entity.Ticket;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Vector;
import java.util.logging.Level;

public class RequestSerializer {
    private OutputStream out;

    public RequestSerializer(OutputStream out) {
        this.out = out;
    }

    public RequestSerializer() {}

    public void serialize(RemoteCommand o) {
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(o, out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public synchronized Vector<Ticket> deserialize(String xml) throws Exception {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vector.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Vector<Ticket>) unmarshaller.unmarshal(new StringReader(xml.trim()));
        } catch (JAXBException e) {

            e.printStackTrace();
            throw new Exception();
        }
    }

}
