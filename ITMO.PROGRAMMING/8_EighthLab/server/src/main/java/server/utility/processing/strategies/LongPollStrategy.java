package server.utility.processing.strategies;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import server.utility.CollectionHandler;
import server.utility.network.RequestDeserializer;
import server.utility.response.Response;
import shared.command.UsableCommand;
import shared.entity.Ticket;

import java.io.StringWriter;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LongPollStrategy implements CommandStrategy{

    static private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Response execute(UsableCommand cmd) {
        synchronized (this) {
            while(!CollectionHandler.hasNewData) {
                try {
                    counter.incrementAndGet();
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (counter.decrementAndGet() == 0) {
            CollectionHandler.hasNewData = false;
        }
        return new Response(true, serialize(new CollectionHandler().getCollection()));
    }

    private synchronized String serialize(Vector<Ticket> collection) {
    try {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Vector.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(collection, sw);
        return sw.toString();
    } catch (JAXBException e) {
        e.printStackTrace();
        return null;
    }
    }

}
