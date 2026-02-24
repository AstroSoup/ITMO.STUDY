package server.utility.processing.strategies;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import server.utility.CollectionHandler;
import server.utility.response.Response;
import shared.command.LongPoll;
import shared.command.UsableCommand;
import shared.entity.Ticket;
import shared.entity.TicketVectorWrapper;

import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class LongPollStrategy implements CommandStrategy {

    private static final Object LOCK = new Object();

    private static final long TIMEOUT_MS = 25_000L; // e.g. 25 seconds

    @Override
    public Response execute(UsableCommand cmd) {
        // 1) Immediately check if there's new data. If so, return right away.
        if (((LongPoll) cmd).getVersion() < CollectionHandler.version) {
            Vector<Ticket> full = new CollectionHandler().getCollection();
            return new Response(true, serialize(full));
        }

        // 2) If no new data right now, hang up to TIMEOUT_MS on the shared LOCK.
        Instant start = Instant.now();

        synchronized (LOCK) {
            try {
                long remaining = TIMEOUT_MS;
                while (((LongPoll) cmd).getVersion() >= CollectionHandler.version && remaining > 0) {
                    LOCK.wait(remaining);
                    // compute how much time is left
                    remaining = TIMEOUT_MS - Duration.between(start, Instant.now()).toMillis();
                }
            } catch (InterruptedException e) {
            }
        }

        if (((LongPoll) cmd).getVersion() < CollectionHandler.version) {
            Vector<Ticket> full = new CollectionHandler().getCollection();
            return new Response(true, serialize(full));
        } else {
            return new Response(true, "");
        }
    }


    private String serialize(Vector<Ticket> collection) {
        try {
            StringWriter sw = new StringWriter();
            TicketVectorWrapper wrapper = new TicketVectorWrapper(collection);
            wrapper.setVersion(CollectionHandler.version);
            JAXBContext context = JAXBContext.newInstance(TicketVectorWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Object getLock() {
        return LOCK;
    }
}
