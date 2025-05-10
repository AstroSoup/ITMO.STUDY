package client.utility;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import shared.command.RemoteCommand;

import java.io.OutputStream;

public class RequestSerializer {
    private OutputStream out;

    public RequestSerializer(OutputStream out) {
        this.out = out;
    }

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
}
