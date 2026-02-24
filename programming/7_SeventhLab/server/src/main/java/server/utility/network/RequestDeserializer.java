package server.utility.network;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import server.utility.CollectionHandler;
import shared.CommandHandler;
import shared.command.*;
import server.auth.AuthHandler;

import java.io.*;
import java.lang.reflect.Field;
import java.util.logging.*;


/**
 * Класс использующийся для десереализации команд
 */
public class RequestDeserializer {


    private final Logger DESERIALIZERLOGGER = Logger.getLogger("server.utility.network.RequestDeserializer");

    public RequestDeserializer() {
        DESERIALIZERLOGGER.setUseParentHandlers(true);
    }

    public synchronized UsableCommand deserialize(String xml) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new StringReader(xml));
            reader.readLine();
            String name = reader.readLine();
            String className = "shared.command." + name.substring(1, 2).toUpperCase() + name.substring(2, name.length() - 1);
            DESERIALIZERLOGGER.log(Level.FINE, "Принята команда: " + className);
            DESERIALIZERLOGGER.finer(xml.trim());
            Class<?> clazz = Class.forName(className);

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (UsableCommand) unmarshaller.unmarshal(new StringReader(xml.trim()));
        } catch (JAXBException | ClassNotFoundException e) {

            DESERIALIZERLOGGER.log(Level.SEVERE, "Команда была интерпретирована некорректно" + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }
}
